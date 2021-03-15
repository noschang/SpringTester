--------------------------------------------------------------------------------
-- Creates a development database for the main/master branch
--------------------------------------------------------------------------------

CREATE USER MAIN_DEV
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO MAIN_DEV;
GRANT ALL PRIVILEGES TO MAIN_DEV;
GRANT SELECT_CATALOG_ROLE TO MAIN_DEV;

--------------------------------------------------------------------------------
-- Creates a test database for the main/master branch
--------------------------------------------------------------------------------

CREATE USER MAIN_TEST
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO MAIN_TEST;
GRANT ALL PRIVILEGES TO MAIN_TEST;
GRANT SELECT_CATALOG_ROLE TO MAIN_TEST;

--------------------------------------------------------------------------------
-- Creates a development database for branch #1
--------------------------------------------------------------------------------

CREATE USER BRANCH1_DEV
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO BRANCH1_DEV;
GRANT ALL PRIVILEGES TO BRANCH1_DEV;
GRANT SELECT_CATALOG_ROLE TO BRANCH1_DEV;

--------------------------------------------------------------------------------
-- Creates a test database for branch #1
--------------------------------------------------------------------------------

CREATE USER BRANCH1_TEST
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO BRANCH1_TEST;
GRANT ALL PRIVILEGES TO BRANCH1_TEST;
GRANT SELECT_CATALOG_ROLE TO BRANCH1_TEST;

--------------------------------------------------------------------------------
-- Creates a development database for branch #2
--------------------------------------------------------------------------------

CREATE USER BRANCH2_DEV
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO BRANCH2_DEV;
GRANT ALL PRIVILEGES TO BRANCH2_DEV;
GRANT SELECT_CATALOG_ROLE TO BRANCH2_DEV;

--------------------------------------------------------------------------------
-- Creates a test database for branch #2
--------------------------------------------------------------------------------

CREATE USER BRANCH2_TEST
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO BRANCH2_TEST;
GRANT ALL PRIVILEGES TO BRANCH2_TEST;
GRANT SELECT_CATALOG_ROLE TO BRANCH2_TEST;

--------------------------------------------------------------------------------
-- Creates a development database for branch #3
--------------------------------------------------------------------------------

CREATE USER BRANCH3_DEV
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO BRANCH3_DEV;
GRANT ALL PRIVILEGES TO BRANCH3_DEV;
GRANT SELECT_CATALOG_ROLE TO BRANCH3_DEV;

--------------------------------------------------------------------------------
-- Creates a test database for branch #3
--------------------------------------------------------------------------------

CREATE USER BRANCH3_TEST
	IDENTIFIED BY YxKscyw47PadvfTrzt3R
	DEFAULT TABLESPACE USERS
	TEMPORARY TABLESPACE TEMP	
	QUOTA UNLIMITED ON USERS
	ACCOUNT UNLOCK;

GRANT CONNECT, RESOURCE TO BRANCH3_TEST;
GRANT ALL PRIVILEGES TO BRANCH3_TEST;
GRANT SELECT_CATALOG_ROLE TO BRANCH3_TEST;

--------------------------------------------------------------------------------