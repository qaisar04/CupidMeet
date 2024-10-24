document.getElementById('authForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    const name = document.getElementById('name').value;
    const group = document.getElementById('group').value;

    // Здесь вы можете сохранить имя и группу, если нужно
    console.log(`ФИО: ${name}, Группа: ${group}`);

    // Скрываем контейнер авторизации и показываем контейнер с тестом
    document.getElementById('auth-container').style.display = 'none';
    document.getElementById('test-container').style.display = 'block';

    loadQuestions(); // Функция для загрузки вопросов
});

function loadQuestions() {
    const questions = [
        { question: "Сұрақ 1: Python дегеніміз - __________, әмбебап бағдарламалау тілі.", answers: ["төмен деңгейлі", "Жоғары деңгейлі", "Веб-сервер деңгейлі", "Компиляцияланатын денгейлі"], correct: [1] },
        { question: "Сұрақ 2: Шартты операторлар дегеніміз не?", answers: ["логикалық операторлар", "мәліметтерді сақтау әдісі", "Шарттың орындалуына байланысты кодтың белгілі бір бөлігінің орындалуын қамтамасыз ететін басқару құрылымы", "Файлдарды ашу әдісі" , "айнымалыларды анықтау әдісі"], correct: [2] },
        { question: "Сұрақ 3: Python-ды алғаш жасаған кім?", answers: ["Гвидо ван Россум", "Джеймс Гослинг", "Линус Торвальдс", "Гидо ван Россум" , "Бьорн Страуструп"], correct: [0] },
        { question: "Сұрақ 4: if операторы қандай жағдайларда қолданылады?", answers: ["Мәтіндік деректерді сақтау үшін", "Шарты шын болған кезде белгілі бір кодты орындау үшін", "Программаның аяқталуын көрсету үшін", "Тізімдерді өңдеу үшін" , "Массивтермен жұмыс жасау үшін"], correct: [1] },
        { question: "Сұрақ 5: Python қандай тілге жатады?", answers: ["Интерпретацияланатын тіл", "Ассемблер тілі", "Компиляцияланатын тіл", "Интерпретатор" , "Жүйелік бағдарламалау тілі"], correct: [0] },
        { question: "Сұрақ 6: else операторы қандай қызмет атқарады?", answers: ["Мәліметтерді файлға жазу" ,"Басқа шартты тексеру" ,"Шарт жалған болған кезде орындалатын кодты көрсетеді", "Программа тоқтайды" ,"Ешқандай қызмет атқармайды"], correct: [2] },
        { 
            question: "Сұрақ 7: Python-да кең таралған IDE қандай?", 
            answers: ["Eclipse", "PyCharm", "NetBeans", "Visual Studio Code", "Emacs"], 
            correct: [1, 3] 
        },
        { question: "Сұрақ 8: elif операторы не үшін қолданылады?", answers: ["Қосымша шарттарды тексеру үшін", "Шартты қайталау үшін", "Шарттарды салыстыру үшін", "Тек мәтіндермен жұмыс істеу үшін", "Ешқандай қызмет атқармайды"], correct: [0] },
        { question: "Сұрақ 9: Python интерпретаторы не істейді?", answers: ["Бағдарламаны машиналық кодқа айналдырады және оны орындайды", "Кодты компиляциялайды", "Тек синтаксистік қателерді тексереді", "Кодты талдайды, бірақ орындамайды", "Кодты орындайды және оны машиналық кодқа айналдырады"], correct: [0, 4] },
        { question: "Сұрақ 10: Кіріктірілген шарттар дегеніміз не?", answers: ["Бір функцияның ішінде бірнеше айнымалыны қолдану", "Бір шарттың ішінде басқа шартты тексеру", "Шарттарды жою әдісі", "Логикалық операторларды пайдалану", "Функцияларды шақыру"], correct: [1] },
        { question: "Сұрақ 11: Төмендегі мәліметтер типтерінің қайсысы Python-да бар?", answers: ["string, char, byte", "int, float, bool", "list, tuple", "map, set", "complex, dict"], correct: [1, 4] },
        { question: "Сұрақ 12: Логикалық операторлар қандай мақсатта қолданылады?", answers: ["Мәліметтерді файлдан оқуға", "Массивтермен жұмыс жасауға", "Бірнеше шартты бір уақытта тексеруге және біріктіруге", "Тек бүтін сандармен жұмыс жасауға", "Мәліметтерді сорттауға"], correct: [2] },
        { question: "Сұрақ 13: print() функциясының міндеті не?", answers: ["Мәліметтерді файлға жазу", "Мәліметтерді экранға шығару", "Логтарды серверге жіберу", "Файлдан деректерді оқу", "Программаның шығу кодын қайтару"], correct: [1, 4] },
        { question: "Сұрақ 14: Python тілінде негізгі логикалық операторларға не жатады?", answers: ["+, -, *, /", "and, or, not", "++, --, **", "int, str, float", "for, while, break"], correct: [1] },
        { question: "Сұрақ 15: Python тілінде айнымалыны жариялау үшін қандай синтаксис қолданылады?", answers: ["int x = 5", "x := 5", "x = 5", "declare x", "let x = 5"], correct: [2, 1] },
        { question: "Сұрақ 16: Төмендегі операторлардың қайсысы екі мәннің теңдігін тексереді?", answers: ["!=", ">=", "==", "<", "and"], correct: [2] },
        { question: "Сұрақ 17: input() функциясы не істейді?", answers: ["Пайдаланушыдан деректерді енгізуді сұрайды", "Файлдан деректерді оқиды", "Мәліметтерді серверге жібереді", "Деректерді шифрлайды", "Тек қана бүтін сандарды енгізуге мүмкіндік береді"], correct: [0, 1] },
        { question: "Сұрақ 18: Төмендегі логикалық операторлармен бірге қолданылатын қандай операторлар шарттарды салыстыру үшін пайдаланылады?", answers: ["+, -", "==, !=, <, >", "print(), input()", "for, while", "int(), float()"], correct: [1] },
        { question: "Сұрақ 19: Python тілінде код блоктарын қалай белгілеу керек?", answers: ["Табуляция немесе интервал арқылы", "Фигурлы жақшалармен {}", "begin және end сөздерімен", "Қос нүктеден кейінгі интервал арқылы", "Нүктелі үтірден кейін бос орын қалдыру арқылы"], correct: [0, 3] },
        { question: "Сұрақ 20: Python тілінде пайдаланушыдан мәліметтерді енгізу үшін қандай функция қолданылады?", answers: ["output()", "write()", "input()", "read()", "open()"], correct: [2] },
        { question: "Есеп 1: Төмендегі код қандай нәтиже береді? temperature = -5 if temperature > 0: print(\"Жылы\") elif temperature == 0: print(\"Нөлдік мән\") else: print(\"Суық\")", answers: ["Жылы", "Нөлдік мән", "Суық", "Қате шығады", "Ештеңе шығарылмайды"], correct: [2] },
        { question: "Есеп 2: Төмендегі кодтың нәтижесі қандай болады, егер пайдаланушы 8 және 4 сандарын енгізсе? # Пайдаланушыдан екі санды енгізу x = int(input(\"Бірінші санды енгізіңіз: \")) y = int(input(\"Екінші санды енгізіңіз: \")) # Қосынды және айырманы есептеу kosyndy = x + y ayirma = x - y # Қосындыны тексеру if kosyndy > 10: print(\"Қосынды 10-дан үлкен\") else: print(\"Қосынды 10-дан кіші немесе тең\") # Айырманы тексеру if ayirma < 0: print(\"Айырмасы теріс сан\") else: print(\"Айырмасы оң немесе нөл\")", answers: ["Қосынды 10-дан кіші немесе тең. Айырмасы теріс сан", "Қосынды 10-дан кіші немесе тең. Айырмасы оң немесе нөл", "Қосынды 10-дан үлкен. Айырмасы теріс сан", "Қосынды 10-дан үлкен. Айырмасы оң немесе нөл"], correct: [1] },
        { question: "Есеп 3: Есептің шарты: Пайдаланушыдан үш санды енгізіп, олардың ең үлкенін табыңыз. Егер ең үлкен сан 50-ден асса, \"Ең үлкен сан 50-ден үлкен\" деп шығарыңыз. Егер 50-ден кіші болса, \"Ең үлкен сан 50-ден кіші немесе тең\" деп шығарыңыз. Пайдаланушы 45, 60, және 35 сандарын енгізсе, кодтың нәтижесі қандай болады? # Пайдаланушыдан үш санды енгізу a = int(input(\"Бірінші санды енгізіңіз: \")) b = int(input(\"Екінші санды енгізіңіз: \")) c = int(input(\"Үшінші санды енгізіңіз: \")) # Ең үлкен санды табу max_san = max(a, b, c) # Ең үлкен санды тексеру if max_san > 50: print(\"Ең үлкен сан 50-ден үлкен\") else: print(\"Ең үлкен сан 50-ден кіші немесе тең\")", answers: ["Ең үлкен сан 50-ден кіші немесе тең", "Ең үлкен сан 50-ден үлкен", "Қате шығады", "Ештеңе шығарылмайды"], correct: [1] },
        { question: "Есеп 4: Есептің шарты: Пайдаланушыдан екі санды енгізіп, олардың көбейтіндісін табыңыз. Егер көбейтінді 100-ден артық болса, \"Көбейтінді 100-ден артық\" деп шығарыңыз. Егер 100-ден аз немесе тең болса, \"Көбейтінді 100-ден аз немесе тең\" деп шығарыңыз. Пайдаланушы 8 және 15 сандарын енгізсе, кодтың нәтижесі қандай болады? # Пайдаланушыдан екі санды енгізу x = int(input(\"Бірінші санды енгізіңіз: \")) y = int(input(\"Екінші санды енгізіңіз: \")) # Көбейтіндіні есептеу kobeitindi = x * y # Көбейтіндіні тексеру if kobeitindi > 100: print(\"Көбейтінді 100-ден артық\") else: print(\"Көбейтінді 100-ден аз немесе тең\")", answers: ["Көбейтінді 100-ден артық", "Көбейтінді 100-ден аз немесе тең", "Қате шығады", "Ештеңе шығарылмайды"], correct: [0] },
        { question: "Есеп 5: Есептің шарты: Пайдаланушыдан екі санды енгізіп, олардың бөліндісін табыңыз. Егер бірінші сан екінші санға қалдықсыз бөлінсе, \"Қалдық жоқ\" деп шығарыңыз. Егер қалдық болса, \"Қалдық бар\" деп шығарыңыз. Пайдаланушы 9 және 3 сандарын енгізсе, кодтың нәтижесі қандай болады? # Пайдаланушыдан екі санды енгізу x = int(input(\"Бірінші санды енгізіңіз: \")) y = int(input(\"Екінші санды енгізіңіз: \")) # Бөлінді мен қалдықты тексеру if x % y == 0: print(\"Қалдық жоқ\") else: print(\"Қалдық бар\")", answers: ["Қалдық жоқ", "Қалдық бар", "Қате шығады", "Ештеңе шығарылмайды"], correct: [0] },
    ]
    
    const testContainer = document.getElementById('test');
    testContainer.innerHTML = ''; // Очищаем предыдущий тест

    questions.forEach((q, index) => {
        const questionElement = document.createElement('div');
        questionElement.classList.add('question');

        const questionText = document.createElement('h3');
        questionText.innerText = q.question;
        questionElement.appendChild(questionText);

        q.answers.forEach((answer, answerIndex) => {
            const answerLabel = document.createElement('label');
            answerLabel.innerText = answer;

            const answerInput = document.createElement('input');
            answerInput.type = 'checkbox'; // Изменил на чекбокс
            answerInput.name = `question${index}`; // Уникальное имя для каждого вопроса
            answerInput.value = answerIndex; // Значение будет индексом ответа

            answerLabel.prepend(answerInput);
            questionElement.appendChild(answerLabel);
            questionElement.appendChild(document.createElement('br'));
        });
        questionElement.correct = q.correct; // Сохраняем правильные ответы
        testContainer.appendChild(questionElement);
    });
}

document.getElementById('finish-test').addEventListener('click', function() {
    const questions = document.querySelectorAll('.question');
    const name = document.getElementById('name').value;
    const group = document.getElementById('group').value;
    let score = 0;

    questions.forEach((question, index) => {
        const checkedAnswers = Array.from(question.querySelectorAll('input[type="checkbox"]:checked')).map(input => parseInt(input.value));
        const correctAnswers = question.correct; // Получаем правильные ответы из сохраненного значения

        // Сравниваем выбранные ответы с правильными
        if (JSON.stringify(checkedAnswers.sort()) === JSON.stringify(correctAnswers.sort())) {
            score += 1; // Увеличиваем счет за каждый правильный ответ
        }
    });

    const totalPoints = score * 4; // Каждый правильный ответ стоит 4 балла
    const maxScore = questions.length * 4; // Максимально возможный балл
    const percentageScore = (totalPoints / maxScore) * 100; // Вычисляем процент

    if (name && group) { 

    // Сохранение результата в localStorage
    const results = JSON.parse(localStorage.getItem('testResults')) || [];
    results.push({ name, group, score: percentageScore });
    localStorage.setItem('testResults', JSON.stringify(results));
    alert(`Тест завершен! Ваш балл: ${totalPoints} из ${maxScore} (${percentageScore.toFixed(2)}%)`);
    console.log("JavaScript подключен и работает!");
    }else {
        alert("Пожалуйста, введите ФИО и группу.");  
    }

});
