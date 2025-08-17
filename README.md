#Lottery

## POST	/draws	
Создаёт новый активный тираж

<img width="675" height="453" alt="image" src="https://github.com/user-attachments/assets/bff668cf-3ae9-46a2-a134-08a355e86a4f" />

При наличии откротого тиража закрывает его и открывает новый.

## POST	/tickets	
Body: {"numbers": [5 чисел], "draw_id": 1}
Купить билет	

<img width="655" height="585" alt="image" src="https://github.com/user-attachments/assets/d3517bff-e06d-40f8-bf49-e4ddd6a23c27" />

Реализованы проверки на наличие и статус тиража, уникальность, количество и сооответствие диапазону чисел.



## POST	/draws/{draw_id}/close	
Завершить тираж	
Генерирует 5 случайных чисел-победителей, определяет победителей

<img width="538" height="541" alt="image" src="https://github.com/user-attachments/assets/2bd8f2d8-120a-4920-89a4-b4050084598b" />



## GET	/draws/{draw_id}/results	
Получить результаты	
Показывает выигрышные числа и все билеты

<img width="459" height="669" alt="image" src="https://github.com/user-attachments/assets/21615fc3-6d43-4505-a100-bbe43212d700" />
