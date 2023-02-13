# Upday exercise

## Selected approach
For the exercise, I decided to create an API that would be hosted on an AWS APIGateway.

## Architecture
The code has a hexagonal architecture, separated into use cases (actions), domain and infrastructure.
I didn't want to put a framework like SpringBoot because I think this APIGateway could run on an AWS Lambda, and for that you need lighter frameworks because of the cold start problem. Dependency injection is still pending, it could be done through Kotlin configuration files that would cache dependencies on demand.

## API
To complete the API, it would be necessary to add the mappings, both in the code and in a possible template.yml if we use infrastructure as code.

## Database
As a database I created an in-memory repository, also to help me test the use cases. I would have liked to implement a DynamoDB but it is not possible due to limited time. This DynamoDB would follow the single-table pattern and would have as PK/SK the article id. Then I would add several GSIs to be able to search by author, keyword and dates. I would also add some integration tests with localstack running on a Docker.
On the security side, I would implement the CheckAuthority use case, using some custom authorizer to validate the authorities.

## Testing
For the testing part, I followed the Object Mother pattern. the unit tests of the use cases are complete. Regarding the handler tests, I have added one of the classes as an example.

# Acknowledgments
I hope you like my way of programming. I'm sorry I don't have more time, I think it is a long exercise to complete it 100%. Thank you very much for the opportunity. 
