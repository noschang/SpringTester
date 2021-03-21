-------------------------------------------------------
-- Table creation DDL
-------------------------------------------------------

CREATE TABLE "INSTITUTION_USER" (
    "INSTITUTION_ID" NUMBER(19, 0) CONSTRAINT "NN_#INST_USER_$INSTID" NOT NULL ENABLE,
    "USER_ID" NUMBER(19, 0) CONSTRAINT "NN_#INST_USER_$USERID" NOT NULL ENABLE,
    
    CONSTRAINT "FK_#INST_USER_#INST" FOREIGN KEY ("INSTITUTION_ID") REFERENCES "INSTITUTION" ("ID") ENABLE,
    CONSTRAINT "FK_#INST_USER_#USER" FOREIGN KEY ("USER_ID") REFERENCES "USER" ("ID") ENABLE
);

-------------------------------------------------------
-- Indexes
-------------------------------------------------------

-- Unique index
CREATE UNIQUE INDEX "UK_#INST_USER_$IID_$UID" ON "INSTITUTION_USER"("INSTITUTION_ID","USER_ID");

-------------------------------------------------------
-- Data insertion
-------------------------------------------------------

INSERT INTO "INSTITUTION_USER"("INSTITUTION_ID","USER_ID")
	SELECT 2445,1 FROM DUAL UNION ALL
    SELECT 2445,2 FROM DUAL UNION ALL
    SELECT 2445,3 FROM DUAL UNION ALL
    SELECT 2434,4 FROM DUAL UNION ALL
    SELECT 2434,5 FROM DUAL;
COMMIT;

-------------------------------------------------------
