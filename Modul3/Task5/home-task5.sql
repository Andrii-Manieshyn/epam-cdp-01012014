UPDATE accounts set balance = 9999 WHERE (account_type=1 OR account_type=3) AND balance > 10000 AND balance < 12000;


/

   CREATE SEQUENCE  "ANDRII_MANIESHYN"."SEQUENCE_TIME_TABLE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
/


  CREATE TABLE "ANDRII_MANIESHYN"."TIME_TABLE" 
   (	"ID" NUMBER NOT NULL ENABLE, 
	"RUNNING_TIME" NUMBER(*,0), 
	 CONSTRAINT "TIME_TABLE_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

  CREATE OR REPLACE TRIGGER "ANDRII_MANIESHYN"."TIME_TABLE_ID_TRIGGER" 
   before insert on "ANDRII_MANIESHYN"."TIME_TABLE" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select SEQUENCE_TIME_TABLE.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "ANDRII_MANIESHYN"."TIME_TABLE_ID_TRIGGER" ENABLE;
 

 /

create or replace PACKAGE bank_utils AS
  PROCEDURE p_check_account (account_id IN  NUMBER,  account_balance IN NUMBER,  is_okay IN OUT BOOLEAN);
  PROCEDURE p_destroy_data;
  PROCEDURE p_destroy_data_forall;
END bank_utils;

/
create or replace PACKAGE BODY bank_utils AS
  PROCEDURE p_check_account (account_id IN  NUMBER,  account_balance IN NUMBER,  is_okay IN OUT BOOLEAN) IS
  BEGIN
    IF (mod(account_id*account_balance,2) = 0) THEN
      is_okay := TRUE;
    ELSE
      is_okay := FALSE;
    END IF;
  END P_CHECK_ACCOUNT;
  
  
PROCEDURE p_destroy_data  IS
	time_stamp INTEGER;
	is_valid BOOLEAN;
BEGIN
	time_stamp := dbms_utility.get_time();
	FOR account_rec
      IN (SELECT account_id, balance
            FROM accounts)
   LOOP
      p_check_account (account_rec.account_id,
                         account_rec.balance,
                         is_valid);

      IF is_valid
      THEN
         UPDATE accounts set balance = balance + balance * .10 WHERE account_rec.account_id = account_id;
      END IF;
   END LOOP;
  
  
	 INSERT INTO TIME_TABLE (RUNNING_TIME) VALUES ((dbms_utility.get_time() - time_stamp));
  END p_destroy_data;
  
 
 
   PROCEDURE p_destroy_data_forall  IS
  
  time_stamp INTEGER;
  is_valid BOOLEAN;
  
  TYPE accounts_ids_t IS TABLE OF accounts.account_id%TYPE
         INDEX BY PLS_INTEGER; 
  l_accounts_ids  accounts_ids_t;
  l_accounts_balances  NUMBER;
  l_is_valid_balances  accounts_ids_t;

	BEGIN

  time_stamp := dbms_utility.get_time();
  SELECT account_id	
  BULK COLLECT INTO l_accounts_ids
  FROM accounts;
     
     FOR indx IN 1 .. l_accounts_ids.COUNT
     LOOP    
         SELECT balance	
         INTO l_accounts_balances
         FROM accounts WHERE l_accounts_ids(indx)=account_id;
     
        Dbms_Output.Put_Line(l_accounts_balances);
      
         p_check_account (l_accounts_ids (indx),
                           l_accounts_balances,
                           is_valid);

        IF is_valid
        THEN
           l_is_valid_balances (l_is_valid_balances.COUNT + 1) :=
              l_accounts_ids (indx);
        END IF;

     END LOOP;
 
      FORALL indx IN 1 .. l_is_valid_balances.COUNT
        UPDATE accounts acc
           SET acc.balance =
                    acc.balance
                  + acc.balance * .10
         WHERE acc.account_id = l_is_valid_balances (indx);

  INSERT INTO TIME_TABLE (RUNNING_TIME) VALUES ((dbms_utility.get_time() - time_stamp));

  END p_destroy_data_forall;
 
 
  
END bank_utils;
 