# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Changed
- Use our public fork of TIDAL's AndroidX Media library 

## [0.0.40] - 2024-05-24
### Added
- Added support for Fragmented MP4. This is needed to be able to play those files directly, which is a new way of playing back certain files coming from our backend.
- Added back version parameter in Player constructor, but this time it is optional.

### Changed
- Depend on EventProducer 0.3.0

### Fixed
- Fix broken broadcast playback: Wrong parameter order for djSessionId and streamingSessionId.

### Removed
- Don't explicitly allow chunkless preparation on hls. This is already set as default.

## [0.0.39] - 2024-05-15
### Fixed
- Use the FlacExtractor from decoder package. This uses LibflacAudioRenderer and has better support than the one that was currently in use. This is needed so we have as broad support as possible.

### Removed
- Remove version as parameter from Player constructor.

## [0.0.38] - 2024-02-08
### Added
- Initial release of the TIDAL Player module
### Removed
- Usage of CredentialsProvider.getLatestCredentials
- Stop reporting "progress" events
