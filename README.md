# react-native-login-with-amazon

## Getting started

`$ npm install react-native-login-with-amazon --save`

### Mostly automatic installation

`$ react-native link react-native-login-with-amazon`

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`

- Add `import com.loginwithamazon.RNReactNativeLoginWithAmazonPackage;` to the imports at the top of the file
- Add `new RNReactNativeLoginWithAmazonPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-login-with-amazon'
   project(':react-native-login-with-amazon').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-login-with-amazon/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-login-with-amazon')
   ```
## Usage

```javascript
import RNReactNativeLoginWithAmazon from 'react-native-login-with-amazon';

// TODO: What to do with the module?
RNReactNativeLoginWithAmazon;
```
