-------------------------------------------------------
-- Table creation DDL
-------------------------------------------------------

CREATE TABLE "USER" (
    "ID" NUMBER(19, 0) CONSTRAINT "NN_#USER_$ID" NOT NULL ENABLE,
    "UUID" RAW(16) CONSTRAINT "NN_#USER_$UUID" NOT NULL ENABLE,
    "ENTITY_VERSION" NUMBER(19, 0) CONSTRAINT "NN_#USER_$ENTVER" NOT NULL ENABLE,

    "NAME" VARCHAR2(128 CHAR) CONSTRAINT "NN_#USER_$NAME" NOT NULL ENABLE,
    "NAME_NORMALIZED" VARCHAR2(128 CHAR) CONSTRAINT "NN_#USER_$NAMENORM" NOT NULL ENABLE,

    "LOGIN" VARCHAR2(64 CHAR) CONSTRAINT "NN_#USER_$LOGIN" NOT NULL ENABLE,
    "PASSWORD" VARCHAR2(60 CHAR) CONSTRAINT "NN_#USER_$PASSW" NOT NULL ENABLE,

    "ROLES" VARCHAR2(64 CHAR) CONSTRAINT "NN_#USER_$ROLES" NOT NULL ENABLE,

    "ACTIVE" NUMBER(1, 0) CONSTRAINT "NN_#USER_$ACTIVE" NOT NULL ENABLE,
    
    CONSTRAINT "PK_#USER_$ID" PRIMARY KEY ("ID") ENABLE
);

-------------------------------------------------------
-- Table constraints
-------------------------------------------------------

-- Check constraint for ID. Ranges from 1 to JAVA Long.MAX_VALUE
ALTER TABLE "USER" ADD CONSTRAINT "CHK_#USER_$ID" CHECK ("ID" BETWEEN 1 AND 9223372036854775807) ENABLE;

-- Check constraint for UUID. The length must be exactly 16, but the "LENGTH" function
-- always returns the value doubled, so we're checking for length = 32
ALTER TABLE "USER" ADD CONSTRAINT "CHK_#USER_$UUID" CHECK (LENGTH("UUID") = 32) ENABLE;

-- Unique constraint for UUID
ALTER TABLE "USER" ADD CONSTRAINT "UK_#USER_$UUID" UNIQUE ("UUID") ENABLE;

-- Check constraint for ENTITY_VERSION. Ranges from 0 to JAVA Long.MAX_VALUE
ALTER TABLE "USER" ADD CONSTRAINT "CHK_#USER_$ENTVER" CHECK ("ENTITY_VERSION" BETWEEN 0 AND 9223372036854775807) ENABLE;

-- Unique constraint for LOGIN
ALTER TABLE "USER" ADD CONSTRAINT "UK_#USER_$LOGIN" UNIQUE ("LOGIN") ENABLE;

-- Check constraint for ACTIVE. It's a Boolean value, so it must be 0 or 1
ALTER TABLE "USER" ADD CONSTRAINT "CHK_#USER_$ACTIVE" CHECK ("ACTIVE" BETWEEN 0 AND 1) ENABLE;

-------------------------------------------------------
-- Indexes
-------------------------------------------------------

-- Index for searchs
CREATE INDEX "IDX_#USER_$SEARCH" ON "USER" ("LOGIN","PASSWORD","ACTIVE","NAME_NORMALIZED");

-------------------------------------------------------
-- Data insertion
-------------------------------------------------------

INSERT INTO "USER"("ID","UUID","ENTITY_VERSION","NAME","NAME_NORMALIZED","LOGIN","PASSWORD","ROLES","ACTIVE")
	SELECT 1,HEXTORAW('C62A0598584EEA15B7DC5D9DB1399088'),0,'Luiz Fernando Noschang','LUIZ FERNANDO NOSCHANG','noschang','$2a$12$CgDwHLeDBBw3xWUQ7aOAfee5lWW95OVot0QjCb9eIEkuIaYAxtxXm','[USER]',1 FROM DUAL UNION ALL
	SELECT 2,HEXTORAW('B288D9D32169BC6BB45E84BE5324F375'),0,'John Connor','JOHN CONNOR','connor','$2a$12$ULJ75KoOYHkE0PiMnafE/OtmkPsWXVtBiy46ruM2sjYiF3//51U5W','[USER]',1 FROM DUAL UNION ALL
    SELECT 3,HEXTORAW('C571CF39C3742EAF03D9B9FAE34230AB'),0,'Mary Jane Watson','MARY JANE WATSON','jane','$2a$12$FaCVbHBgKy8/yD6Akofvi.kwjNy14LU8KNstalxkl5U.MqOzHBuC2','[USER]',1 FROM DUAL UNION ALL
    SELECT 4,HEXTORAW('EAD39ADC401E6CDAFFE106540566DFA2'),0,'Mike Wazowski','MIKE WAZOWSKI','mike','$2a$12$x1kctCmvfwaTn2IPpgtPTOjm7KlSysyQ/jVdjBuPv6WbXhOssyHXy','[USER]',1 FROM DUAL UNION ALL
    SELECT 5,HEXTORAW('9FB9E9CF191A75E6F245A3CB19BA2969'),0,'Julie Piper','Julie Piper','julie','$2a$12$e/XHPRcyJCligy8.l2ArS.mUvkfAR6OIho9VIQnZYR5qqqH4cysXm','[USER]',1 FROM DUAL;
COMMIT;

-------------------------------------------------------
-- Related sequences
-------------------------------------------------------

CREATE SEQUENCE "SEQ_USER" START WITH 1 MINVALUE 1 INCREMENT BY 1000 CACHE 100 NOCYCLE;

CALL RESET_SEQUENCES();

-------------------------------------------------------
