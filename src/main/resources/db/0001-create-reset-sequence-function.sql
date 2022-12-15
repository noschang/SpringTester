CREATE OR REPLACE PROCEDURE RESET_SEQUENCES AS

	CURSOR FIND_ASSOCIATIONS IS 
		SELECT
			T.TABLE_NAME,
			'SEQ_' || T.TABLE_NAME AS SEQUENCE_NAME,
			CASE 
				WHEN S.SEQUENCE_NAME IS NULL THEN 'NO' 
				ELSE 'YES' 
			END AS PRESENT,
			S.INCREMENT_BY AS ALLOCATION_SIZE,
			S.CACHE_SIZE
		FROM 
		(
			SELECT 
				C.TABLE_NAME,	
				LISTAGG('[' || C.COLUMN_NAME || ']', '') WITHIN GROUP (ORDER BY C.COLUMN_NAME) AS COLUMNS	
			FROM USER_TAB_COLS C WHERE C.COLUMN_NAME IN ('ID', 'UUID', 'ENTITY_VERSION')	
			GROUP BY C.TABLE_NAME
			
		) T
		LEFT JOIN USER_SEQUENCES S ON S.SEQUENCE_NAME = 'SEQ_' || T.TABLE_NAME
		WHERE T.COLUMNS LIKE '%[ENTITY_VERSION]%[ID]%[UUID]%';


	NEXT_ID NUMBER;
BEGIN
	FOR ASSOCIATION IN FIND_ASSOCIATIONS LOOP

		IF ASSOCIATION.PRESENT = 'NO' THEN

			ASSOCIATION.ALLOCATION_SIZE := 1000;
			ASSOCIATION.CACHE_SIZE := 100;
			
		END IF;

		IF ASSOCIATION.PRESENT = 'YES' THEN
		
			EXECUTE IMMEDIATE 'DROP SEQUENCE ' || ASSOCIATION.SEQUENCE_NAME;
		
		END IF;
	
		EXECUTE IMMEDIATE 'SELECT CASE WHEN MAX(ID) IS NULL THEN 1 ELSE (TRUNC(MAX(ID) / ' || ASSOCIATION.ALLOCATION_SIZE ||  ') * ' || ASSOCIATION.ALLOCATION_SIZE ||  ') + ' || ASSOCIATION.ALLOCATION_SIZE ||  ' + 1 END FROM "' || ASSOCIATION.TABLE_NAME || '"'  INTO NEXT_ID;
			
		EXECUTE IMMEDIATE 'CREATE SEQUENCE ' || ASSOCIATION.SEQUENCE_NAME || ' START WITH ' || NEXT_ID || ' MINVALUE 1 MAXVALUE 9223372036854775807 INCREMENT BY ' || ASSOCIATION.ALLOCATION_SIZE ||  ' CACHE ' || ASSOCIATION.CACHE_SIZE || ' NOCYCLE';
		
		DBMS_OUTPUT.PUT_LINE('Sequence "' || ASSOCIATION.SEQUENCE_NAME || '" has been reset to ' || NEXT_ID);

	END LOOP;
END;