cd /BankServerEurekaServer
mvn clean compile package -Dmaven.test.skip

cd /BankServerAdminServer
mvn clean compile package -Dmaven.test.skip

cd /BankServerAuthenticationService
mvn clean compile package -Dmaven.test.skip

cd /BankServerApiGateway
mvn clean compile package -Dmaven.test.skip

cd /BankServerCustomerService
mvn clean compile package -Dmaven.test.skip

cd /BankServerAccountService
mvn clean compile package -Dmaven.test.skip

cd /BankServerTransactionService
mvn clean compile package -Dmaven.test.skip