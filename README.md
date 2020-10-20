# Progetto Java

Implementare in Java un Social Game System (SGS), ovvero un’implementazione della teoria dei giochi evolutivi basato sullo scambio di messaggi fra
giocatori. La descrizione di un SFS è riportata di seguito. Sono consentite
semplificazioni del modello o “variazioni sul tema” (auspicabilmente ben motivate). Il sistema prodotto dovrebbe consentire quantomeno l’implementazione
del gioco della vita di Conway. E possibile, ma non necessario, corredare il siste- `
ma di un’interfaccia grafica che consenta di visualizzare l’evoluzione del gioco
mediante un’animazione.
Si richiede la consegna di un programma funzionante, scritto in Java, accompagnato da uno o pi`u casi studio, anche semplici, che ne mettano in luce
gli aspetti pi`u significativi. Scelte progettuali, implementative e caso studio
andranno discusse in una breve relazione, alla quale andr`a allegato il sorgente
Java, estesamente corredato di commenti.

Milioni di invidividui che interagiscono tra di loro ricevendo qualcosa in input e restituendo 
qualcosa , ogni individuo ha differenti carratteristiche e una % di benessere,
inizialmente tutto ciò che gli individui comunicano è casuale ed è casuale anche il modo in cui 
gli individui prendono un outpunt, a seconda dell'inidividuo , esso puo prendere l'output come 
positivo o negativo e quindi incrementare/decrementare la % di benessere , quando il benessere 
è alto l'individuo crea un figlio che eredita tutte le caratteristiche del padre.
Quando la % di benessere è bassa l'individuo muore.

Man mano che gli individui interagiscono muoiono o si riproducono si crea un'evoluzione e 
sopravvivono solo alcuni tipi di invidivui.

Ogni individuo ha un certo numero di conoscenze che puo aumentare fino a un max.
Ogni individuo è amico di se stesso.

Solo un certo numero di individui è attivo, gli altri sono in attesa di nascere.

Le persone conosciute sono un attributo della classe individuo, questo attributo è un array e ogni
posizione di questo array rapressenta un ruolo (ad esempio: figlio , padre , amico ecc)
Lo stesso individuo puo avere + di un ruolo.

Array costituto da tuple t[0] = ruolo  t[1] = nome individuo conosciuto.

comunicazioni = tentacoli 
dominio board = individui attivi
profilo = stato attuale(messaggi ricevuti)

Es:
I1 dice a I2 7 e I2 risponde 0
I1 per le sue carratteristiche la prende male e la sua % di benessere scende

Restituisco messaggi solo alla persona che me l ha mandato e viceversa.

Ogni giocatore è dotato di una STRATEGIA, funzione che a seconda dei messaggi ricevuti ne restituisce 
un altro.
