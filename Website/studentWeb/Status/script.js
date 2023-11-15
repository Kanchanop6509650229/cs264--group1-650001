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

        const lateFormLink = document.getElementById('LateForm');
      // Add a click event listener to the link
      lateFormLink.addEventListener('click', function (event) {
          // Prevent the default behavior of the link (i.e., prevent it from navigating)
          event.preventDefault();

          // Construct the URL for the next page with the studentId parameter
          const nextPageUrl = `../LateForm/lateform.html?studentId=${user_id}`;

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

      const AddDropLink = document.getElementById('addDropLink');
            // Add a click event listener to the link
            AddDropLink.addEventListener('click', function (event) {
              // Prevent the default behavior of the link (i.e., prevent it from navigating)
              event.preventDefault();

              // Construct the URL for the next page with the studentId parameter
              const nextPageUrl = `../Add_Drop_item/add_drop.html?studentId=${user_id}`;

              // Redirect to the next page
              window.location.href = nextPageUrl;
            });

    // Fetch student data by studentId
        $.ajax({
          type: "GET",
          url: `/api/form/getAllFormById?studentId=${user_id}`,
          contentType: "application/json",
          success: function (formDataArray) {
          console.log("Form data received:", formDataArray);
              if (formDataArray && formDataArray.length > 0) {
                          // Get the table body element
                          var tableBody = document.getElementById("requestTable").getElementsByTagName("tbody")[0];

                          // Iterate over the array and create a row for each object
                          formDataArray.forEach(function (formData, index) {
                              var row = requestTable.insertRow(-1);

                              // Add cells to the row and populate them with data
                              var orderCell = row.insertCell(0);
                              var dateTimeCell = row.insertCell(1);
                              var statusCell = row.insertCell(2);
                              var formNameCell = row.insertCell(3);
                              var studentNameCell = row.insertCell(4);
                              var detailsCell = row.insertCell(5);

                              orderCell.innerHTML = index + 1;
                              dateTimeCell.innerHTML = formData.dateTime; // Replace with the actual property name
                              if (formData.status === 'อนุมัติ') {
                                statusCell.innerHTML = '<div style="display: inline-block; border-radius: 17px; border: 1px solid #0C7348; background: rgba(53, 216, 148, 0.70); padding: 15px 40px;">' + formData.status + '</div>';
                              } else if (formData.status === 'ปฏิเสธ') {
                                statusCell.innerHTML = '<div style="display: inline-block; border-radius: 17px; border: 1px solid #A41B3E; background: rgba(228, 128, 153, 0.70); padding: 15px 40px;">' + formData.status + '</div>';
                              } else {
                                statusCell.innerHTML = '<div style="display: inline-block; border-radius: 17px; border: 1px solid #E9BE2B; background: rgba(253, 225, 129, 0.70); padding: 15px 40px;">' + formData.status + '</div>';
                              }

                              formNameCell.innerHTML = formData.formName;
                              studentNameCell.innerHTML = formData.studentName;
                              detailsCell.innerHTML = '<a href="#"><ion-icon name="folder-open-outline"></ion-icon></a>';

                              // Set border-right style for orderCell
                              orderCell.style.borderRight = 'none';
                              dateTimeCell.style.borderLeft = 'none';
                              dateTimeCell.style.borderRight = 'none';
                              statusCell.style.borderLeft = 'none';
                              statusCell.style.borderRight = 'none';
                              formNameCell.style.borderLeft = 'none';
                              formNameCell.style.borderRight = 'none';
                              studentNameCell.style.borderLeft = 'none';
                              studentNameCell.style.borderRight = 'none';
                              detailsCell.style.borderLeft = 'none';

                              orderCell.style.fontWeight = 'bold';

                              // Set background color based on formData.status
                              if (formData.status === 'อนุมัติ') {
                                row.style.backgroundColor = '#5CE5AC';
                              } else if (formData.status === 'ปฏิเสธ') {
                                row.style.backgroundColor = '#FB9FB6';
                              } else {
                                row.style.backgroundColor = '#F9E6A6';
                              }

                            // Set the width for the statusCell
                            statusCell.style.width = '420px'; // Adjust the width as needed
                            orderCell.style.width = '150px';
                            detailsCell.style.width = '150px';
                            studentNameCell.style.width = '450px';
                            dateTimeCell.style.width = '320px';
                          });
              } else {
                  console.log("No data received for studentId:", user_id);
                  // Log the error to the console for debugging
              }
          },
          error: function (error) {
              console.log("Error fetching student data:", error);
          },
      });

    }
  });


function logout() {
  // Update the sessionStorage variable to indicate the user is not authenticated
  sessionStorage.setItem("authenticated", "false");

  // Redirect to the login page or any other desired page
  window.location.href = "../../index.html"; // Change the URL as needed
}

