cd ./BankServerEurekaServer
start mvn clean compile package -Dmaven.test.skip

cd ./BankServerAdminServer
start mvn clean compile package -Dmaven.test.skip

cd ./BankServerAuthenticationService
start mvn clean compile package -Dmaven.test.skip

cd ./BankServerApiGateway
start mvn clean compile package -Dmaven.test.skip

cd ./BankServerCustomerService
start mvn clean compile package -Dmaven.test.skip

cd ./BankServerAccountService
start mvn clean compile package -Dmaven.test.skip

cd ./BankServerTransactionService
start mvn clean compile package -Dmaven.test.skip