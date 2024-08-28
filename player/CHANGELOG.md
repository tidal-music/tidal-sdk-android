# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.0.50] - 2024-08-28
### Added
- Add MediaProduct.extras

## [0.0.49] - 2024-08-27
### Fixed
- Fix UC requests initially dispatched without credential header

## [0.0.48] - 2024-08-21
### Fixed
- Reset PlaybackEngine in onPlayerError

## [0.0.47] - 2024-08-19
### Fixed
- Loading stuck when switching media products too quickly
- Ignore caches after error (per process and media product)

## [0.0.46] - 2024-07-26
### Fixed
- Fixed loud volume issue

## [0.0.45] - 2024-07-25
### Added
- Allow opt-out of LibflacAudioRenderer usage

## [0.0.44] - 2024-07-19
### Fixed
- Fixed crash related to nullable streaming privileges fields

## [0.0.43] - 2024-06-03
### Added
- Add immersiveaudio param to playbackinfo endpoint

## [0.0.42] - 2024-05-28
### Added
- Add and support playback of new product type

## [0.0.41] - 2024-05-27
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
