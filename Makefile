#! make
package:
	./gradlew clean build

index:
	sudo mkdir -p /data/lucene/namematching
	docker compose -f nameindex.yml up --detach nameindex

run: index
	./gradlew clean bootRun

clean:
	docker compose -f nameindex.yml down nameindex
	docker volume rm sds-webapp2_data_nameindex_local

run-docker: package
	docker compose -f docker-compose.yml up --detach

release:
	../sbdi-install/utils/make-release.sh
