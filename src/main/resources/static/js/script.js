const formulario = document.getElementById("formAgendamento");
const campoData = document.getElementById("dataAgendamento");
const mensagem = document.getElementById("mensagem");

// Bloqueia dias anteriores a hoje no calendário
const hoje = new Date();
const ano = hoje.getFullYear();
const mes = String(hoje.getMonth() + 1).padStart(2, "0");
const dia = String(hoje.getDate()).padStart(2, "0");
campoData.min = `${ano}-${mes}-${dia}`;

formulario.addEventListener("submit", async function(event){
    event.preventDefault();

    const data = campoData.value;
    const hora = document.getElementById("horaAgendamento").value;
    const dataHoraAgendamento = data + "T" + hora;

    // Não deixa agendar no passado (inclui hoje em horário que já passou)
    if (new Date(dataHoraAgendamento) < new Date()) {
        mensagem.textContent = "Escolha uma data e horário que ainda não passaram.";
        return;
    }

    const agendamento = {
        tipoServico: document.getElementById("tipoServico").value,
        tapeceiro: document.getElementById("tapeceiro").value,
        dataHoraAgendamento: dataHoraAgendamento,
        cliente: document.getElementById("cliente").value,
        telefoneCliente: document.getElementById("telefoneCliente").value
    };

    console.log(agendamento);

    try{
        const resposta = await fetch("/agendamentos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(agendamento)
        });

        if (resposta.status === 202) {
            mensagem.textContent = "Agendamento realizado com sucesso!";
            formulario.reset();
        } else if (resposta.status === 409) {
            mensagem.textContent = "Esse horário já está ocupado. Escolha outro.";
        } else {
            mensagem.textContent = "Não foi possível agendar. Confira os dados e tente novamente.";
        }

    }catch (erro){
        mensagem.textContent = "Não foi possível se conectar, tente novamente mais tarde.";
    }
});