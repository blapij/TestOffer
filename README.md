# TestOffer

Possibilité de get et delete avec l'Id et le Username :
	/users/{id}
	/users/name/{username}

La méthode update est problématique:
	La date de naissance et le pays de résidence peuvent être modifiés alors que le checking de l'âge et du pays se font durant le create.

Swagger accessible sur localhost:8080/swagger-ui/
						http://localhost:8080/v2/api-docs