# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.11.1] - 2025-01-15
### Changed
- A version bump with no changes to retrigger building after a dependency issue

## [0.11.0] - 2025-01-15
### Changed
- **BREAKING**: `finalizeLogin()` now expects only the query string component instead of the full redirect URL
- Renamed internal `RedirectUri` type to `RedirectData` for clarity
- Applied code formatting using ktfmt

### Fixed
- Updated OkHttp dependency to address FOSSA scan issue

## [0.10.2] - 2024-09-17
### Changed
- Ensure that setting credentials manually is synced with retrieving them from the backend. This prevents accidental overwrites in local storage.

## [0.10.1] - 2024-08-22
### Fixed
- Fix bug that prevented version 0.10.0 from properly deserializing data stored by older versions

## [0.10.0] - 2024-07-16
### Changed
- Dependency on kotlinx.datetime removed - this makes auth compatible to Android SDK <26
- Because of this change, `Credentials` type's `expires` is now a `Long`. 

## [0.9.2] - 2024-07-03
### Fixed
- Fix bug preventing properly synchronized backend calls when requesting a new token

## [0.9.1] - 2024-05-28
### Changed
- No changes. release was created for technical reasons

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
