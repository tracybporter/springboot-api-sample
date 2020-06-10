# Develop and Deployment Rhythm

1. People: Create enhancement
	1. Review story and acceptance criteria with team in huddle
	1. Cut branch:
	    1. `gcb some-small-enhancement`,
	    1. `git push origin some-small-enhancement`
	1. Test-first code enhancements.
	    1. Add acceptance test. Confirm it fails for the right reasons. Commit and push.
	    1. TDD application changes
	        1. If pairing, ping pong unit/integration and production code changes.
	        1. Commit and push frequently. Preferrably after each green bar.
	1. Confirm acceptance test passes.
	1. Submit pull-request
1. People: Merge enhancement into the master branch.
1. Jenkins: Continuous Integration
	1. Checkout master
	1. Build and run all unit, integration and acceptance tests
	1. Tag as ci-<app-version>
1. Jenkins: Deploy to QA (dependent on previous step)
	1. Checkout latests ci tag
	1. Create jar file and store
	1. Deploy jar to QA environment
	1. Increment application version and push to master
1. Jenkins: Validate QA (time based execution)
	1. Request application version (http://qa-load-balancer/info)
	1. Pull master branch
	1. Checkout tag: ci-<app-version>
	1. Execute acceptance tests against the QA load-balancer/instance
	1. If all pass and git tag qa-<app-version> does not exist, create and push it.

# WHY?
Short feedback loops. That is learn quickly if the last change results in the expected outcome.
