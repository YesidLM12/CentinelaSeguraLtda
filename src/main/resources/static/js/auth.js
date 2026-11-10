document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const errorEmail = document.getElementById('errorusername');
    const errorPassword = document.getElementById('errorpassword');
    const alertaloggin = document.getElementById('alertaloggin');
    const boton = document.getElementById('btnLogin');
    const togglePassword = document.getElementById('togglePassword');

    // Mostrar u ocultar la contraseña
    togglePassword.addEventListener('click', () => {
       const estabaOulta = passwordInput.type === 'password';
       passwordInput.type = estabaOulta ? 'text' : 'password';
       togglePassword.textContent = estabaOulta ? 'Ocultar' : 'Mostrar';
       togglePassword.setAttribute('aria-label', estabaOulta ? 'Ocultar contraseña' : 'Mostrar contraseña');
    });

    //Mostrar un mensaje junto a un campo y marcarlo como inválido
    function mostrarError(campo, elementoMensaje, mensaje) {
       elementoMensaje.textContent = mensaje;
       campo.setAttribute('aria-invalid', mensaje ? 'true' : 'false');
    }

    function limpiarAlertaServidor() {
       if (alertaloggin) {
          alertaloggin.style.display = 'none';
       }
    }

    // Quitar el error mientras el usuario corrige el campo
    emailInput.addEventListener('input', () => {
       mostrarError(emailInput, errorEmail, '');
    });
    
    passwordInput.addEventListener('input', () => {
       mostrarError(passwordInput, errorPassword, '');
    });

    form.addEventListener('submit', (e) => {
       emailInput.value = emailInput.value.trim();
       let valido = true;

       if(!emailInput.value) {
          mostrarError(emailInput, errorEmail, 'El correo electrónico es obligatorio');
          valido = false;
          limpiarAlertaServidor();
       }

       if(!passwordInput.value) {
          mostrarError(passwordInput, errorPassword, 'La contraseña es obligatoria');
          valido = false;
          limpiarAlertaServidor();
       }

       if(!valido) {
          e.preventDefault();
          (emailInput.value ? passwordInput : emailInput).focus();
          return;
       }

       // Evitar doble envio
       boton.disabled = true;
       boton.textContent = 'Ingresando...';
    });
   
    document.getElementById('errorpassword').textContent = '';

    document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;

    const res = await fetch(form.action, {
        method: 'POST',
        headers: { [header]: token },
        body: new URLSearchParams(new FormData(form))
    });

    if (res.redirected) {
        window.location.href = res.url;   // login correcto
    } else {
        const alerta = document.getElementById('alertaLogin');
        alerta.textContent = 'Usuario o contraseña incorrectos';
        alerta.style.display = 'block';
    }
});
});
