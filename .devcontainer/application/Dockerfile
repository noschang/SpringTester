# This image was based on the image from Microsoft VSCode containers 
# https://github.com/microsoft/vscode-dev-containers/tree/master/containers/java

# The image was modified to use AdoptOpenJDK with OpenJ9 JVM instead of
# the default OpenJDK with HotSpot JVM

# [Choice] Java version: 11, 15
ARG JAVA_VERSION="11"

# [Choice] JVM type: openj9, hostspot
ARG JVM_TYPE="openj9"

# Additional images can be built modifyng the line below. For a complete list of 
# images visit: https://hub.docker.com/_/adoptopenjdk?tab=tags

FROM adoptopenjdk:${JAVA_VERSION}-jdk-${JVM_TYPE}

# Install needed packages and setup non-root user. Use a separate RUN statement to add your own dependencies.
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# [Option] Install zsh
ARG INSTALL_ZSH="true"

# [Option] Install oh my
ARG INSTALL_OH_MYS="true"

# [Option] Upgrade OS packages to their latest versions
ARG UPGRADE_PACKAGES="true"

ARG ADDITIONAL_PACKAGES=""

COPY build-files/library-scripts/common-debian.sh /tmp/library-scripts/

RUN bash /tmp/library-scripts/common-debian.sh "${INSTALL_ZSH}" "${USERNAME}" "${USER_UID}" "${USER_GID}" "${UPGRADE_PACKAGES}" "${INSTALL_OH_MYS}" "${ADDITIONAL_PACKAGES}" \
    && if [ ! -d "/docker-java-home" ]; then ln -s "${JAVA_HOME}" /docker-java-home; fi \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/library-scripts

# Create directories for vscode extensions cache, as explained here:
# https://code.visualstudio.com/docs/remote/containers-advanced#_avoiding-extension-reinstalls-on-container-rebuild

RUN mkdir -p /extensions \
    && mkdir -p /extensions/vscode-server \
    && mkdir -p /extensions/vscode-server-insiders \
    && ln -s /extensions/vscode-server /home/$USERNAME/.vscode-server \
    && ln -s /extensions/vscode-server-insiders /home/$USERNAME/.vscode-server-insiders \
    && chown -R ${USERNAME}:${USERNAME} /extensions /home/$USERNAME/.vscode-server /home/$USERNAME/.vscode-server-insiders

# [Option] Install Maven
ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""

# [Option] Install Gradle
ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""

ENV SDKMAN_DIR="/usr/local/sdkman"
ENV PATH="${PATH}:${SDKMAN_DIR}/java/current/bin:${SDKMAN_DIR}/maven/current/bin:${SDKMAN_DIR}/gradle/current/bin"

COPY build-files/library-scripts/java-debian.sh build-files/library-scripts/maven-debian.sh build-files/library-scripts/gradle-debian.sh /tmp/library-scripts/

RUN bash /tmp/library-scripts/java-debian.sh "none" "${SDKMAN_DIR}" "${USERNAME}" "true" \
    && if [ "${INSTALL_MAVEN}" = "true" ]; then bash /tmp/library-scripts/maven-debian.sh "${MAVEN_VERSION:-latest}" "${SDKMAN_DIR}" ${USERNAME} "true"; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then bash /tmp/library-scripts/gradle-debian.sh "${GRADLE_VERSION:-latest}" "${SDKMAN_DIR}" ${USERNAME} "true"; fi \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/library-scripts

# [Option] Install Node.js
ARG INSTALL_NODE="true"
ARG NODE_VERSION="none"

ENV NVM_DIR=/usr/local/share/nvm
ENV NVM_SYMLINK_CURRENT=true \
    PATH="${NVM_DIR}/current/bin:${PATH}"

COPY build-files/library-scripts/node-debian.sh /tmp/library-scripts/

RUN if [ "${INSTALL_NODE}" = "true" ]; then bash /tmp/library-scripts/node-debian.sh "${NVM_DIR}" "${NODE_VERSION}" "${USERNAME}"; fi \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/library-scripts

# [Option] Install graphic libraries
ARG INSTALL_GRAPHIC_LIBS="false"

COPY build-files/library-scripts/graphic-libs-debian.sh /tmp/library-scripts/

RUN if [ "${INSTALL_GRAPHIC_LIBS}" = "true" ]; then bash /tmp/library-scripts/graphic-libs-debian.sh; fi \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/library-scripts

# [Option] Install graphic libraries
ARG INSTALL_SQLPLUS="false"

COPY build-files/library-scripts/sqlplus-debian.sh /tmp/library-scripts/

RUN if [ "${INSTALL_SQLPLUS}" = "true" ]; then bash /tmp/library-scripts/sqlplus-debian.sh; fi \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/library-scripts

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

COPY build-files/lombok-fix /startup/lombok-fix
COPY build-files/startup.sh /startup/

RUN mkdir -p /data
RUN chown ${USERNAME}:${USERNAME} /data

ENTRYPOINT [ "/startup/startup.sh" ]