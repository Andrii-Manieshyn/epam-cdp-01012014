
CREATE OR REPLACE PACKAGE emp_mgmt_test AS
  PROCEDURE test_proc(from_city VARCHAR2, to_city VARCHAR2, expected_result VARCHAR2);                  
END emp_mgmt_test; 
/ 
CREATE OR REPLACE PACKAGE BODY emp_mgmt_test AS
  
      PROCEDURE test_proc(from_city VARCHAR2, to_city VARCHAR2, expected_result VARCHAR2) IS
        route_cities VARCHAR2(20 byte); 
        BEGIN
            route_cities := EMP_MGMT.abbriviation(from_city, to_city); 
            dbms_output.put_line(CASE WHEN route_cities = expected_result 
                  THEN 'Cities test passed successfully.' 
                  ELSE 'Cities test failed.' END); 
            EMP_MGMT.print_last_rout_proc; 
        END test_proc; 
        
END emp_mgmt_test; 