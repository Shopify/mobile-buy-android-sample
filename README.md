![Mobile Buy SDK](https://cloud.githubusercontent.com/assets/5244861/26738020/885c12ac-479a-11e7-8914-2853ec09f89f.png)

# Mobile Buy SDK - Sample Android App

The [Mobile Buy SDK](https://github.com/Shopify/mobile-buy-sdk-android) makes it easy to create custom storefronts in your mobile app, where users can buy products using Google Pay or their credit card. 

The Buy SDK includes a comprehensive sample application that covers the SDK's most common use cases. It's built on best practices and our recommended `ViewModel` architecture. You can use it as a template, a starting point, or a place to cherrypick components as needed. 

## Table of contents

- [Setup](#setup-)
- [Contributions](#contributions-)
- [Help](#help-)
- [License](#license-)


## Setup [⤴](#table-of-contents)

To run the sample application, you can import this repository in Android Studio and run the project on a virtual device that you've created in the AVD manager.

### Provide your own credentials

You may choose to provide credentials to the shop that the app will point to:

1. Create a private app with a Storefront API access token to access the storefront data. The storefront access token acts as the API key.
2. Modify the file `sample/shop.properties` with your credentials.
    
```
SHOP_DOMAIN=<your-shop-here>.myshopify.com
API_KEY=<your-api-key>
```

## Contributions [⤴](#table-of-contents)

We welcome contributions. Please follow the steps in our [contributing guidelines](CONTRIBUTING.md).

## Help [⤴](#table-of-contents)

For help, see the [Android Buy SDK documentation](https://help.shopify.com/en/api/storefront-api/tools/android-buy-sdk) or post questions on [our forum](https://ecommerce.shopify.com/c/shopify-apis-and-technology), in `Shopify APIs & SDKs` section.

## License [⤴](#table-of-contents)

The Mobile Buy SDK is provided under an [MIT License](LICENSE).
