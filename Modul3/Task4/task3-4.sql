create or replace PACKAGE emp_mgmt AS 
   my_exeption EXCEPTION; 
 TYPE driver IS RECORD (
    driver_age NUMBER(3),
    driver_name VARCHAR2(64),
    route_from_to VARCHAR2(64),
    time_of_the_route VARCHAR2(64)
  );
    TYPE driver_list IS TABLE OF driver;
    drivers_list_collection driver_list;
   
    FUNCTION abbriviation (from_city VARCHAR2, to_city VARCHAR2) RETURN VARCHAR2 ;
    PROCEDURE print_last_rout_proc;
    PROCEDURE last_rout_proc;
END emp_mgmt;

/

create or replace PACKAGE BODY EMP_MGMT AS

  FUNCTION abbriviation (from_city VARCHAR2, to_city VARCHAR2) 
    RETURN VARCHAR2 AS
  BEGIN
    RETURN UPPER(REGEXP_REPLACE(from_city, '([eyuioaAEYUIO]+)', ''));
  END abbriviation;
  
  PROCEDURE last_rout_proc IS
  BEGIN
     drivers_list_collection  := driver_list(); 
          FOR drivers_rec IN (SELECT id FROM Driver)  
          LOOP 
              drivers_list_collection.extend; 
              SELECT 
                (extract(year from sysdate) - extract(year from TO_DATE(d.birthday_date , 'dd.mm.rrrr'))), 
                d.name, 
                  abbriviation(ct_from.name, ct_to.name), 
                  CASE WHEN r.end_date IS NULL OR r.start_date IS NULL
                  THEN 0   
                  ELSE (r.end_date - r.start_date) END || 'd' 
              INTO
                drivers_list_collection(drivers_list_collection.count).driver_age, 
                drivers_list_collection(drivers_list_collection.count).driver_name, 
                drivers_list_collection(drivers_list_collection.count).route_from_to, 
                drivers_list_collection(drivers_list_collection.count).time_of_the_route 
              FROM Driver d 
              LEFT JOIN Vehicle2Driver vd ON vd.driver_id = d.id
              LEFT JOIN Route r ON r.id = vd.route_id
              LEFT JOIN City ct_from ON ct_from.id = r.from_id
              LEFT JOIN City ct_to ON ct_to.id = r.to_id
              WHERE rownum = 1 AND d.id = drivers_rec.id
              ORDER BY d.id, r.start_date DESC NULLS LAST; 
          END LOOP; 
            
          EXCEPTION WHEN NO_DATA_FOUND THEN DBMS_OUTPUT.PUT_LINE('No records.'); 
  END last_rout_proc;
  
  
  
  
  
  
      PROCEDURE print_last_rout_proc IS
        BEGIN
          last_rout_proc(); 
          FOR indx IN  drivers_list_collection.FIRST .. drivers_list_collection.LAST
          LOOP 
              dbms_output.put_line(drivers_list_collection(indx).driver_name || ' ' || drivers_list_collection(indx).driver_age 
                                   || ' years old. Last trip: ' || drivers_list_collection(indx).route_from_to || ' ('
                                   || drivers_list_collection(indx).time_of_the_route || ')'); 
          END LOOP; 
        END print_last_rout_proc;  
        
        
        
        
END EMP_MGMT;