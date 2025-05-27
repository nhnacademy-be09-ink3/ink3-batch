DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;

CREATE TABLE BATCH_JOB_INSTANCE (
                                    JOB_INSTANCE_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    VERSION BIGINT,
                                    JOB_NAME VARCHAR(100) NOT NULL,
                                    JOB_KEY VARCHAR(32) NOT NULL,
                                    CONSTRAINT JOB_INST_UN UNIQUE (JOB_NAME, JOB_KEY)
);

CREATE TABLE BATCH_JOB_EXECUTION (
                                     JOB_EXECUTION_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     VERSION BIGINT,
                                     JOB_INSTANCE_ID BIGINT NOT NULL,
                                     CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP,
                                     START_TIME DATETIME,
                                     END_TIME DATETIME,
                                     STATUS VARCHAR(10),
                                     EXIT_CODE VARCHAR(2500),
                                     EXIT_MESSAGE VARCHAR(2500),
                                     LAST_UPDATED DATETIME,
                                     JOB_CONFIGURATION_LOCATION VARCHAR(2500),
                                     CONSTRAINT JOB_INST_EXEC_FK FOREIGN KEY (JOB_INSTANCE_ID)
                                         REFERENCES BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
);

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS (
                                            JOB_EXECUTION_ID BIGINT NOT NULL,
                                            PARAMETER_NAME VARCHAR(100) NOT NULL,
                                            PARAMETER_TYPE VARCHAR(100) NOT NULL,
                                            PARAMETER_VALUE VARCHAR(2500),
                                            IDENTIFYING CHAR(1) NOT NULL,
                                            CONSTRAINT JOB_EXEC_PARAMS_FK FOREIGN KEY (JOB_EXECUTION_ID)
                                                REFERENCES BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

CREATE TABLE BATCH_STEP_EXECUTION (
                                      STEP_EXECUTION_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      VERSION BIGINT,
                                      STEP_NAME VARCHAR(100),
                                      JOB_EXECUTION_ID BIGINT NOT NULL,
                                      START_TIME DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      END_TIME DATETIME,
                                      STATUS VARCHAR(10),
                                      COMMIT_COUNT BIGINT,
                                      READ_COUNT BIGINT,
                                      FILTER_COUNT BIGINT,
                                      WRITE_COUNT BIGINT,
                                      READ_SKIP_COUNT BIGINT,
                                      WRITE_SKIP_COUNT BIGINT,
                                      PROCESS_SKIP_COUNT BIGINT,
                                      ROLLBACK_COUNT BIGINT,
                                      EXIT_CODE VARCHAR(2500),
                                      EXIT_MESSAGE VARCHAR(2500),
                                      LAST_UPDATED DATETIME,
                                      CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      CONSTRAINT JOB_EXEC_STEP_FK FOREIGN KEY (JOB_EXECUTION_ID)
                                          REFERENCES BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT (
                                             JOB_EXECUTION_ID BIGINT PRIMARY KEY,
                                             SHORT_CONTEXT VARCHAR(2500),
                                             SERIALIZED_CONTEXT TEXT,
                                             CONSTRAINT JOB_EXEC_CTX_FK FOREIGN KEY (JOB_EXECUTION_ID)
                                                 REFERENCES BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT (
                                              STEP_EXECUTION_ID BIGINT PRIMARY KEY,
                                              SHORT_CONTEXT VARCHAR(2500),
                                              SERIALIZED_CONTEXT TEXT,
                                              CONSTRAINT STEP_EXEC_CTX_FK FOREIGN KEY (STEP_EXECUTION_ID)
                                                  REFERENCES BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
);

-- MariaDB에서는 시퀀스가 기본적으로 없으므로 생략하거나 사용 안함
