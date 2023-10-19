# Sensitive Data Service (SDS)

## Setup

### Config and data directory
Create data directory at `/data/sds` and populate as below (it is easiest to symlink the config files to the ones in this repo):
```
$ tree /data/sds
/data/sds
├── config
│   ├── sds-config.properties -> /home/manash/projects/sds-webapp2/sbdi/data/config/sds-config.properties
│   └── system-message.json
├── legacy-sds-config.properties -> /home/manash/projects/sds-webapp2/sbdi/data/legacy-sds-config.properties
├── sensitive-species.xml
├── sensitivity-categories.xml -> /home/manash/projects/sds-webapp2/sbdi/data/sensitivity-categories.xml
└── sensitivity-zones.xml -> /home/manash/projects/sds-webapp2/sbdi/data/sensitivity-zones.xml
```

### Index
The service requires a taxonomic search index (Lucene) which can be setup using the docker image ghcr.io/biodiversitydata-se/ala-name-matching-index
It can be setup on a local file system using the `nameindex.yml` for local development or as a part of the composition while running in docker.
```

make index
```

## Usage
Run locally:
```
make run
```

Build and run in Docker (using Tomcat).
```
make run-docker
```

Make a release. This will create a new tag and push it. A new Docker image will be built on Github.
```
$ make release
Current version: 1.0.1. Enter the new version (or press Enter for 1.0.2): 
Updating to version 1.0.2
Tag 1.0.2 created and pushed.
```
