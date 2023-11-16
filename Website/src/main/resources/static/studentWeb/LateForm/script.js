document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is authenticated
    const isAuthenticated = sessionStorage.getItem("authenticated");
    if (isAuthenticated !== "true") {
      // If not authenticated, redirect to the login page
      window.location.href = "../../index.html";
      return;
    } else {

    // Add event listener for screen resize
    window.addEventListener("resize", function () {
        checkScreenSize();
      });
    
      // Function to check and display SweetAlert for screen size
      function checkScreenSize() {
        const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    
        // Check if the screen size is less than 1800px
        if (screenWidth < 1490) {
          Swal.fire({
            icon: "warning",
            title: "เราขอแนะนำให้ใช้เบราว์เซอร์ในขนาดเต็มจอ หรือความกว้างมากกว่า 1490px",
            showCancelButton: false,
            confirmButtonText: "OK",
          }).then(() => {
            // Check screen size again after the user clicks OK
            checkScreenSize();
          });
        }
      }
    
      // Initial check when the page loads
      checkScreenSize();

        const urlParams = new URLSearchParams(window.location.search);
        const user_id = urlParams.get("studentId");
        const QuitFormLink = document.getElementById('QuitForm');
  
        // Add a click event listener to the link
        QuitFormLink.addEventListener('click', function (event) {
            // Prevent the default behavior of the link (i.e., prevent it from navigating)
            event.preventDefault();
  
            // Construct the URL for the next page with the studentId parameter
            const nextPageUrl = `../QuitForm/QuitForm.html?studentId=${user_id}`;
  
            // Redirect to the next page
            window.location.href = nextPageUrl;
        });

      // Get the "ขอเพิ่ม/ถอนรายวิชา (drop w)" link element
      const addDropLink = document.getElementById('addDropLink');

      // Add a click event listener to the link
      addDropLink.addEventListener('click', function (event) {
          // Prevent the default behavior of the link (i.e., prevent it from navigating)
          event.preventDefault();

          // Construct the URL for the next page with the studentId parameter
          const nextPageUrl = `../Add_Drop_item/add_drop.html?studentId=${user_id}`;

          // Redirect to the next page
          window.location.href = nextPageUrl;
      });

        const OtherFormLink = document.getElementById('otherFormLink');

                    // Add a click event listener to the link
                    OtherFormLink.addEventListener('click', function (event) {
                        // Prevent the default behavior of the link (i.e., prevent it from navigating)
                        event.preventDefault();

                        // Construct the URL for the next page with the studentId parameter
                        const nextPageUrl = `../OtherForm/otherform.html?studentId=${user_id}`;

                        // Redirect to the next page
                        window.location.href = nextPageUrl;
                    });

        const statusLink = document.getElementById('statusLink');

        // Add a click event listener to the link
        statusLink.addEventListener('click', function (event) {
        // Prevent the default behavior of the link (i.e., prevent it from navigating)
            event.preventDefault();

            // Construct the URL for the next page with the studentId parameter
            const nextPageUrl = `../Status/status.html?studentId=${user_id}`;

            // Redirect to the next page
            window.location.href = nextPageUrl;
        });
    }
  });


function logout() {
  // Update the sessionStorage variable to indicate the user is not authenticated
  sessionStorage.setItem("authenticated", "false");

  // Redirect to the login page or any other desired page
  window.location.href = "../../index.html"; // Change the URL as needed
}
    
// Save input values to localStorage when the form is submitted
function submitForm(event) {
    event.preventDefault(); // Prevent the default form submission

    // Create a JSON object to hold the form data
    const jsonData = {};

    // Add values from enabled input fields
    const formData = $("#lateForm").serializeArray();

    // Get the studentId from the URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    const studentId = urlParams.get("studentId");

    // Convert formData to a JSON object
        formData.forEach(function (entry) {
            jsonData[entry.name] = entry.value;
        });

        // Add the studentId to the jsonData object
        jsonData["studentId"] = studentId;
        jsonData["formStatus"] = "รออาจารย์ที่ปรึกษาอนุมัติ";

        // Manually get the selected value from the semester dropdown
        const semesterDropdown = $("#semester");
        jsonData["semester"] = semesterDropdown.val();

    // Send an AJAX request to your controller with the correct data format
    $.ajax({
      type: "POST",
      url: "/api/form/lateForm", // Replace with your actual endpoint
      data: JSON.stringify(jsonData), // Convert to JSON format
      contentType: "application/json", // Set content type to JSON
      success: function (response) {
        // Show a success message with SweetAlert2
        Swal.fire({
          title: "Success",
          text: response,
          icon: "success",
          showConfirmButton: false,
          timer: 2000, // Auto-close the alert after 2 seconds
        });
      },
      error: function (error) {
        // Show an error message with SweetAlert2
        Swal.fire({
          title: "Error",
          text: "Failed to save data. Please try again.",
          icon: "error",
        });
      },
    });
  }