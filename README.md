Mysql is used as application database and h2 in memory database is used for batch metadata. See applocation.properties.
DataSourceConfig class of Config package contains the configuration to configure application datasource and batch metadata datasource. 
BatchConfig class of config package contains the configuration to create JobRepository bean to use batch metaadata datasource.
secondJob class of config package is creating a job - jobTwo. This job reads from a csv file and inserts into instructor_detail table of application database.
