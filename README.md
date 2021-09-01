# Liquibase Data
Example repository for creating your own Liquibase extensions

## Getting Started
### Requirements
*  [Liquibase 4.4.3](https://github.com/liquibase/liquibase/releases)
*  [Titan 0.5.2](https://github.com/titan-data/titan/releases)

## Installation
Download the latest [release](https://github.com/liquibase/liquibase-data/releases) and extract the contents to `LIQUIBASE/lib`.

## Usage
Liquibase Data commands are available from the Command Line Interface. 
```bash
liquibase data [OPTIONS] [COMMAND]
```

For any command, additional help information is available with the `--help` option. Example:
```bash
liquibase data clone --help

Clone a remote repository to local repository

Usage: liquibase data clone [OPTIONS]

      --commit=PARAM       commit to checkout
                           (liquibase.command.commit OR liquibase.command.data.
                             clone.commit)
                           (LIQUIBASE_COMMAND_COMMIT OR
                             LIQUIBASE_COMMAND_DATA_CLONE_COMMIT)

      --disable-port-mapping[=PARAM]
                           disable default port mapping from container to
                             localhost
                           (liquibase.command.disablePortMapping OR liquibase.
                             command.data.clone.disablePortMapping)
                           (LIQUIBASE_COMMAND_DISABLE_PORT_MAPPING OR
                             LIQUIBASE_COMMAND_DATA_CLONE_DISABLE_PORT_MAPPING)
                           [deprecated: --disablePortMapping]

  -h, --help               Show this help message and exit
      --name=PARAM         optional new name for repository
                           (liquibase.command.name OR liquibase.command.data.
                             clone.name)
                           (LIQUIBASE_COMMAND_NAME OR
                             LIQUIBASE_COMMAND_DATA_CLONE_NAME)

      --parameters=PARAM   provider specific parameters. key=value format.
                             comma separated
                           (liquibase.command.parameters OR liquibase.command.
                             data.clone.parameters)
                           (LIQUIBASE_COMMAND_PARAMETERS OR
                             LIQUIBASE_COMMAND_DATA_CLONE_PARAMETERS)

      --tags=PARAM         filter latest commit by tags. comma separated
                           (liquibase.command.tags OR liquibase.command.data.
                             clone.tags)
                           (LIQUIBASE_COMMAND_TAGS OR
                             LIQUIBASE_COMMAND_DATA_CLONE_TAGS)

      --uri=PARAM          [REQUIRED] URI of the remote to clone from
                           (liquibase.command.uri OR liquibase.command.data.
                             clone.uri)
                           (LIQUIBASE_COMMAND_URI OR
                             LIQUIBASE_COMMAND_DATA_CLONE_URI)

```

## Available Commands
|Command |Description |
|:--- | :--- |
|checkout |Checkout a specific commit |
|clone |Clone a remote repository to local repository |
|commit |Commit current data state |
|cp |Copy data into a repository |
|delete |Delete objects from titan |
|diff | |
|generate-changelog |Generate a changelog [deprecated: generateChangelog] |
|log |List commits for a repository |
|ls |List repositories |
|migrate |Migrate an existing docker database container to titan repository. |
|pull |Pull a new data state from remote |
|push |Push data state to remote |
|remote | |
|rm |Remove a repository |
|run |Create repository and start container |
|start |Start a container for a repository |
|stop |Stop a container for a repository |
|tag |Tag objects in titan |
|update |Deploy any changes in the changelog file that have not been deployed |

## Report Issues
Please report all issues and suggestions [here](https://github.com/liquibase/liquibase-data/issues).

## Want to help?
Want to file a bug or improve documentation? Excellent! Read up on our guidelines for [contributing](https://www.liquibase.org/community/index.html)!

## License
Liquibase is [licensed under the Apache 2.0 License](https://github.com/liquibase/liquibase-data/blob/main/LICENSE).
