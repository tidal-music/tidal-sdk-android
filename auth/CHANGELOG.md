# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.9.0] - 2024-05-14
### Changed
- Improve tidalAuthServiceBaseUrl and tidalLoginServiceBaseUrl usage in AuthConfig

## [0.8.0] - 2024-05-07
### Added
- Internal modifier to required classes
- Return values to both login finalisation functions
### Removed
- Traces of scope strings

## [0.7.0] - 2024-04-30
### Removed
- Regex validation of scopes
- Depreciate Scope

## [0.6.0] - 2024-04-29
### Fixed
- Several small issues
### Added
- Add documentation comments
### Changed
- Check for ApiErrorSubStatus.value
