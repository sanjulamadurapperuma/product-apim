DROP TABLE IF EXISTS AM_SYSTEM_APPS;
CREATE SEQUENCE AM_API_SYSTEM_APPS_SEQUENCE START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS AM_SYSTEM_APPS (
  ID INTEGER NOT NULL DEFAULT NEXTVAL('AM_API_SYSTEM_APPS_SEQUENCE'),
  NAME VARCHAR(50) NOT NULL,
  CONSUMER_KEY VARCHAR(512) NOT NULL,
  CONSUMER_SECRET VARCHAR(512) NOT NULL,
  CREATED_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (NAME),
  UNIQUE (CONSUMER_KEY),
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS AM_API_CLIENT_CERTIFICATE;
CREATE TABLE AM_API_CLIENT_CERTIFICATE (
  TENANT_ID INTEGER NOT NULL,
  ALIAS VARCHAR(45) NOT NULL,
  API_ID INTEGER NOT NULL,
  CERTIFICATE BYTEA NOT NULL,
  REMOVED BOOLEAN NOT NULL DEFAULT '0',
  TIER_NAME VARCHAR (512),
  FOREIGN KEY (API_ID) REFERENCES AM_API (API_ID) ON DELETE CASCADE,
  PRIMARY KEY (ALIAS, TENANT_ID, REMOVED)
);

ALTER TABLE AM_POLICY_SUBSCRIPTION ADD MONETIZATION_PLAN VARCHAR(25) NULL DEFAULT NULL,
   ADD FIXED_RATE VARCHAR(15) NULL DEFAULT NULL, 
   ADD BILLING_CYCLE VARCHAR(15) NULL DEFAULT NULL, 
   ADD PRICE_PER_REQUEST VARCHAR(15) NULL DEFAULT NULL, 
   ADD CURRENCY VARCHAR(15) NULL DEFAULT NULL;

CREATE TABLE IF NOT EXISTS AM_MONETIZATION_USAGE_PUBLISHER (
	ID VARCHAR(100) NOT NULL,
	STATE VARCHAR(50) NOT NULL,
	STATUS VARCHAR(50) NOT NULL,
	STARTED_TIME VARCHAR(50) NOT NULL,
	PUBLISHED_TIME VARCHAR(50) NOT NULL,
	PRIMARY KEY(ID)
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE AM_API_COMMENTS
  DROP COLUMN COMMENT_ID,
  ADD COLUMN COMMENT_ID VARCHAR(255) NOT NULL DEFAULT uuid_generate_v1(),
  ADD PRIMARY KEY (COMMENT_ID);

ALTER TABLE AM_API_RATINGS
  DROP COLUMN RATING_ID,
  ADD COLUMN RATING_ID VARCHAR(255) NOT NULL DEFAULT uuid_generate_v1(),
  ADD PRIMARY KEY (RATING_ID);

CREATE TABLE IF NOT EXISTS AM_NOTIFICATION_SUBSCRIBER (
    UUID VARCHAR(255),
    CATEGORY VARCHAR(255),
    NOTIFICATION_METHOD VARCHAR(255),
    SUBSCRIBER_ADDRESS VARCHAR(255) NOT NULL,
    PRIMARY KEY(UUID, SUBSCRIBER_ADDRESS)
);

ALTER TABLE AM_EXTERNAL_STORES
ADD LAST_UPDATED_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE AM_API
  ADD API_TYPE VARCHAR(10) NULL DEFAULT NULL;

CREATE SEQUENCE AM_API_PRODUCT_MAPPING_SEQUENCE START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS AM_API_PRODUCT_MAPPING (
  API_PRODUCT_MAPPING_ID INTEGER DEFAULT nextval('am_api_product_mapping_sequence'),
  API_ID INTEGER,
  URL_MAPPING_ID INTEGER,
FOREIGN KEY (API_ID) REFERENCES AM_API(API_ID) ON DELETE CASCADE,
FOREIGN KEY (URL_MAPPING_ID) REFERENCES AM_API_URL_MAPPING(URL_MAPPING_ID) ON DELETE CASCADE,
PRIMARY KEY(API_PRODUCT_MAPPING_ID)
);

DROP TABLE IF EXISTS AM_REVOKED_JWT;
CREATE TABLE IF NOT EXISTS AM_REVOKED_JWT (
  UUID VARCHAR(255) NOT NULL,
  SIGNATURE VARCHAR(2048) NOT NULL,
  EXPIRY_TIMESTAMP BIGINT NOT NULL,
  TENANT_ID INTEGER DEFAULT -1,
  TOKEN_TYPE VARCHAR(15) DEFAULT 'DEFAULT',
  TIME_CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (UUID)
);

-- UMA tables --
DROP TABLE IF EXISTS IDN_UMA_RESOURCE;
DROP SEQUENCE IF EXISTS IDN_UMA_RESOURCE_SEQ;
CREATE SEQUENCE IDN_UMA_RESOURCE_SEQ;
CREATE TABLE IDN_UMA_RESOURCE (
  ID INTEGER DEFAULT NEXTVAL('IDN_UMA_RESOURCE_SEQ') NOT NULL,
  RESOURCE_ID VARCHAR(255),
  RESOURCE_NAME VARCHAR(255),
  TIME_CREATED TIMESTAMP NOT NULL,
  RESOURCE_OWNER_NAME VARCHAR(255),
  CLIENT_ID VARCHAR(255),
  TENANT_ID INTEGER DEFAULT -1234,
  USER_DOMAIN VARCHAR(50),
  PRIMARY KEY (ID)
);

CREATE INDEX IDX_RID ON IDN_UMA_RESOURCE (RESOURCE_ID);

CREATE INDEX IDX_USER ON IDN_UMA_RESOURCE (RESOURCE_OWNER_NAME, USER_DOMAIN);

DROP TABLE IF EXISTS IDN_UMA_RESOURCE_META_DATA;
DROP SEQUENCE IF EXISTS IDN_UMA_RESOURCE_META_DATA_SEQ;
CREATE SEQUENCE IDN_UMA_RESOURCE_META_DATA_SEQ;
CREATE TABLE IDN_UMA_RESOURCE_META_DATA (
  ID INTEGER DEFAULT NEXTVAL ('IDN_UMA_RESOURCE_META_DATA_SEQ') NOT NULL,
  RESOURCE_IDENTITY INTEGER NOT NULL,
  PROPERTY_KEY VARCHAR(40),
  PROPERTY_VALUE VARCHAR(255),
  PRIMARY KEY (ID),
  FOREIGN KEY (RESOURCE_IDENTITY) REFERENCES IDN_UMA_RESOURCE (ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS IDN_UMA_RESOURCE_SCOPE;
DROP SEQUENCE IF EXISTS IDN_UMA_RESOURCE_SCOPE_SEQ;
CREATE SEQUENCE IDN_UMA_RESOURCE_SCOPE_SEQ;
CREATE TABLE IDN_UMA_RESOURCE_SCOPE (
  ID INTEGER DEFAULT NEXTVAL ('IDN_UMA_RESOURCE_SCOPE_SEQ') NOT NULL,
  RESOURCE_IDENTITY INTEGER NOT NULL,
  SCOPE_NAME VARCHAR(255),
  PRIMARY KEY (ID),
  FOREIGN KEY (RESOURCE_IDENTITY) REFERENCES IDN_UMA_RESOURCE (ID) ON DELETE CASCADE
);

CREATE INDEX IDX_RS ON IDN_UMA_RESOURCE_SCOPE (SCOPE_NAME);

DROP TABLE IF EXISTS IDN_UMA_PERMISSION_TICKET;
DROP SEQUENCE IF EXISTS IDN_UMA_PERMISSION_TICKET_SEQ;
CREATE SEQUENCE IDN_UMA_PERMISSION_TICKET_SEQ;
CREATE TABLE IDN_UMA_PERMISSION_TICKET (
  ID INTEGER DEFAULT NEXTVAL('IDN_UMA_PERMISSION_TICKET_SEQ') NOT NULL,
  PT VARCHAR(255) NOT NULL,
  TIME_CREATED TIMESTAMP NOT NULL,
  EXPIRY_TIME TIMESTAMP NOT NULL,
  TICKET_STATE VARCHAR(25) DEFAULT 'ACTIVE',
  TENANT_ID INTEGER DEFAULT -1234,
  PRIMARY KEY (ID)
);

CREATE INDEX IDX_PT ON IDN_UMA_PERMISSION_TICKET (PT);

DROP TABLE IF EXISTS IDN_UMA_PT_RESOURCE;
DROP SEQUENCE IF EXISTS IDN_UMA_PT_RESOURCE_SEQ;
CREATE SEQUENCE IDN_UMA_PT_RESOURCE_SEQ;
CREATE TABLE IDN_UMA_PT_RESOURCE (
  ID INTEGER DEFAULT NEXTVAL ('IDN_UMA_PT_RESOURCE_SEQ') NOT NULL,
  PT_RESOURCE_ID INTEGER NOT NULL,
  PT_ID INTEGER NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (PT_ID) REFERENCES IDN_UMA_PERMISSION_TICKET (ID) ON DELETE CASCADE,
  FOREIGN KEY (PT_RESOURCE_ID) REFERENCES IDN_UMA_RESOURCE (ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS IDN_UMA_PT_RESOURCE_SCOPE;
DROP SEQUENCE IF EXISTS IDN_UMA_PT_RESOURCE_SCOPE_SEQ;
CREATE SEQUENCE IDN_UMA_PT_RESOURCE_SCOPE_SEQ;
CREATE TABLE IF NOT EXISTS IDN_UMA_PT_RESOURCE_SCOPE (
ID INTEGER DEFAULT NEXTVAL ('IDN_UMA_PT_RESOURCE_SCOPE_SEQ') NOT NULL,
  PT_RESOURCE_ID INTEGER NOT NULL,
  PT_SCOPE_ID INTEGER NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (PT_RESOURCE_ID) REFERENCES IDN_UMA_PT_RESOURCE (ID) ON DELETE CASCADE,
FOREIGN KEY (PT_SCOPE_ID) REFERENCES IDN_UMA_RESOURCE_SCOPE (ID) ON DELETE CASCADE
);
