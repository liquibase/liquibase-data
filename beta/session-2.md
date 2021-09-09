# Session 2: Team development using remotes

Over the next hour, you will build on your experience in Session 1. Session 2 focuses on team development with remotes. This allows you to share database state between team members.

> For assistance, questions, or comments, please contact us via [Discord.](https://discord.gg/NVpqM7nNnT)

> After completing the Session, please provide feedback [here.](https://forms.gle/SFjPgLCMYeUbWzR87)


# Creating a Remote Location for Liquibase Data

You will be storing your data files in an Amazon Web Services S3 bucket. Your team members will need access to the bucket you create below.

## Step 1: Verify that AWS CLI is installed

AWS CLI helps configure your system so that Liquibase can access the S3 bucket. If you do not have AWS CLI installed, you can download the AWS CLI [here.](https://aws.amazon.com/cli/)

## Step 2: Configure your AWS CLI

The AWS CLI requires an AWS Access Key ID and AWS Secret Access Key. You can complete this process (or verify your existing configuration) with the following command:

    aws configure

> Enter your AWS Access Key ID and AWS Secret Access Key. Default region name and Default output are not necessary

## Step 3: Create an S3 Bucket.

For this session, create an AWS S3 Bucket using the Web Console. The default options tend to be secure. We recommend not changing those for this session.

Using the AWS Web Console, create a bucket in the region of your choice. (We'll be using `lb-remote-bucket` for the examples.) After configuring in the web console, accept defaults and select Create.

> S3 bucket names are globally unique. `lb-remote-bucket` can not be re-used. Keep track of your bucket name for the steps below. 

## Step 4: Create a folder in your new S3 Bucket

We require a subfolder in your bucket. Feel free to use any name you would like.  For this example, we created a folder named `data` in via the AWS Web Console. Navigate to your bucket in the AWS Web Console and add a folder, accept the defaults, and select "Create".

## Step 5: Initialize your remote

In this step, you will push your latest commit to your S3 remote location.

    liquibase data remote add --uri=s3://<your-s3-bucket-name>/data/ 
    liquibase data push

NOTE: Make sure to use a trailing slash on your `--uri` argument.

## Step 6: Verify your commit exists in the remote repository

Now that you have pushed your database change to the remote, you should look to verify. 

    aws s3 ls --recursive <your-s3-bucket-name>

> You should see a directory named after the commit id of your latest commit.

## Step 7: Push other commits to your remote

The previous push will only push the latest commit to the remote, you will now push the previous commits. First, identify the commits using the following command:

    liquibase data log

For each commit you want to push, perform the following:

    liquibase data push --commit=<commit id>

Verify the commits are persisted to the remote using the following command:

    aws s3 ls --recursive <your-s3-bucket-name>

# Team Development with Liquibase

This is a team exercise. Partner with another user that has created a remote via the previous step.

## Step 1: Clone a remote repository

For this part of the session, you will be working with a team member to share database commits between machines.  They will need access to the S3 bucket. Using the AWS CLI command to list directories will verify access.

    aws s3 ls --recursive <your-s3-bucket-name>

Once this is successful, have your team member use the following command.

    liquibase data clone --uri=s3://<your-s3-bucket-name>/data --name=<a unique repository>

NOTE: If you have used `repository` in `liquibase.properties` and are using a new repository name, update `liquibase.properties` to the new name. 

> The `--name` option allows you to name the new repository some another arbitrary name. This is similar to adding a different folder name with `git clone`. If you have a repository with the same name as your team member, you will need to use this option.

NOTE: You will need to stop (`liquibase data stop`) the previous container. Another option is to use `--disable-port-mapping=true`.

## Step 2: Use the development cycle

Like any other development cycle, (create change, commit, push, pull) you will work on sharing change with your team member. It does not matter which team member goes first; simply take turns.

### 1. Make a change to the database and commit the change

Using a database IDE or Liquibase, make a change to the database. Adding data to a table would be a good choice.

    liquibase data commit --message="Another commit"

### 2. Verify the commit exists with the following
Execute this command to return a list of commits stored on your local machine.

    liquibase data log

### 3. Verify that the commit does NOT exist on the remote with the following

Execute this command to return a list of commits remotely stored remotely in the S3 bucket:

    liquibase data remote log


### 4. Push the commit to the remote
Execute this command to copy your local change to the remote:

    liquibase data push

### 5. Verify that the commit DOES exist on the remote
Execute this command to return a list of commits remotely stored in the S3 bucket:

    liquibase data remote log

Verify the results are different than your previous `remote log` execution two steps back.

### 6. Verify the commit exists
Have your team member execute the following command:

    liquibase data remote log

Verify the results your results.

### 7. Have your team member pull the commit

    liquibase data pull --commit=<commit id>

Your team member should connect to the database and verify your specific change.

### 8. Perform these operations again, but have the team member drive the changes.

## Step 3: Provide feedback on your experience.

Visit [here](https://forms.gle/SFjPgLCMYeUbWzR87) to provide feedback.

You can also find us on [Discord](https://discord.gg/NVpqM7nNnT) in #beta-release or DM Robert Reeves (r2liquibase#9789) to schedule time to discuss your experience.

## Step 4: Continue onto Session 3

Session 2 can be found [here.](session-3.md)
