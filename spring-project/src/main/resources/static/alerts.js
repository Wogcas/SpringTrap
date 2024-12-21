function showSuccessAlert(message) {
    Swal.fire({
        icon: 'success',
        title: '¡Éxito!',
        text: message,
        confirmButtonColor: '#3085d6',
        confirmButtonText: 'Aceptar'
    });
}

function showErrorAlert(message) {
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: message,
        confirmButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const error = document.getElementById('errorMessage')?.value;
    const success = document.getElementById('successMessage')?.value;

    if (error) {
        showErrorAlert(error);
    }

    if (success) {
        showSuccessAlert(success);
    }
});