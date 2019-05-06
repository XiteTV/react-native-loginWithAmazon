using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace React.Native.Login.With.Amazon.RNReactNativeLoginWithAmazon
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNReactNativeLoginWithAmazonModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNReactNativeLoginWithAmazonModule"/>.
        /// </summary>
        internal RNReactNativeLoginWithAmazonModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNReactNativeLoginWithAmazon";
            }
        }
    }
}
