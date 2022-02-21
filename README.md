# FakeStoreDemoApp
Android fake store demo app built with the API available at https://fakestoreapi.com/docs.

When testing the application, use the following credentials:<br>
**•	Username: mor_2314<br>
•	Password: 83r5^_**<br>

The Android application uses the following API functionalities:<br>
•	User login<br>
•	Get all products<br>
•	Get one product<br>
•	Add new product (create updated cart)<br>
•	Get cart items<br>
•	Download image (product photo)<br>

The application should work fine on most Android devices from API level 21 (Android 5.0) onwards. Targeted display: 1080x1920, 420dpi. Please note that if your resolution and font settings are very different compared to FHD, this might change the way in which the app is displayed.

**Screens/Activities**:
1. Login screen<br>
![image](https://user-images.githubusercontent.com/59920637/155029037-714cb124-9bd9-4b33-b9f2-2b118937b1a2.png)<br>
Use the credentials above to login. If you provide invalid credentials, you'll receive an "invalid credentials" message error. The password is sent in plain-text through the API, since Fake Store API doesn't support any cryptography.<br>
2. Main screen<br>
![image](https://user-images.githubusercontent.com/59920637/155029555-da606962-bdf0-4868-bdb0-1828dbd2582b.png)<br>
On this page, the list of products is displayed. It includes the product photo, category, rating and price for the first 10 products. Clicking a product will open the product page, while clicking the cart photo will open the cart for the current user.<br>
3. Cart screen<br>
![image](https://user-images.githubusercontent.com/59920637/155030073-aacdda84-7058-452b-ae88-6d63b8facd47.png)<br>
On this page, the products found in the current cart for the logged in user (user with ID #2 hardcoded, because the API doesn't provide user ID after login) are displayed. The total price is computed. Clicking on the "Order" button will send the user back to the home screen, but without sending an actual "order" API request (Fake Store API doesn't provide such a command).<br>
4. Product screen<br>
![image](https://user-images.githubusercontent.com/59920637/155030479-e95b24fc-5483-435f-b524-39945d23bc74.png)<br>
This page provides the full details of the selected product, including the product photo, title (product name), description, category, rating and # of reviews. When the quantity is changed, the total price is updated. Clicking "Add to cart" will create a new cart based on the existing one and sent it through the API. However, this won't be reflected in the cart, since the API is not persistent.


