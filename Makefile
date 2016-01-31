intall: 
	@./gradlew install
update:
	@./gradlew install --refresh-dependencies
test:
	@./gradlew test	
idea: 
	@./gradlew idea
doc:
	@./gradlew javadoc
clean: 
	@./gradlew clean 
