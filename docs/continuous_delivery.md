# Continuos Delivery

Our project uses GitHub action and Heroku pipeline to perform continuous delivery. 

## GitHub action

Tests will be run on every feature branch push.

![feature_test](https://user-images.githubusercontent.com/122768314/236303290-335b8857-3e6a-4350-9746-bab4f19d84e6.PNG)

Pull requests will be reviewed and merge into main after all comments are resolved.

![pull_request_review](https://user-images.githubusercontent.com/122768314/236304902-c40c5d40-4a85-4735-b3d0-8f2dd5b17723.PNG)

## Heroku pipeline

After merging a new pull request to main, if all tests passed, main will be automatically deploy on staging

![autodeploy_staging](https://user-images.githubusercontent.com/122768314/236305987-0fbfd863-ae4b-43ff-9fee-4249b6a34de3.PNG)

After testing our deployment on staging, if we are happy with the current version of the apps, main will be merged into production branch. 

Similar to staging, production apps will be deployed automatically based on production branch if tests pass.

Alternatively, we can manually promote apps from staging to production.

![pipeline](https://user-images.githubusercontent.com/122768314/236307051-349f977d-3f0d-469d-96cf-7450efc3e50e.PNG)

This way we can rapidly write, test, deploy, and deliver new version of our apps to the customers.