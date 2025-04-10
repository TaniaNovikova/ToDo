package de.mydev.app.model;
// Импорт аннотации @Data из библиотеки Lombok.
// Lombok автоматически генерирует методы (геттеры, сеттеры, equals, hashCode, toString) для полей класса.
import lombok.Data;

// Импорты аннотаций из Java Persistence API (JPA), используемых для отображения сущностей на таблицы базы данных.
import jakarta.persistence.*;

// Импорт класса LocalDateTime из пакета java.time для работы с датой и временем.
//import javax.management.relation.*;
import java.time.LocalDateTime;

@Data // Аннотация Lombok: генерирует стандартные методы (геттеры, сеттеры и др.), сокращая количество шаблонного кода.
@Entity // Аннотация JPA: указывает, что класс является сущностью и должен быть сопоставлен с таблицей в базе данных.
@Table(name = "users")
// Аннотация JPA: задаёт имя таблицы, в которой будут храниться данные данной сущности.
// Название "users" выбрано вместо "user", чтобы избежать конфликтов с зарезервированными словами в PostgreSQL.
public class User
{
    @Id
    // Аннотация JPA: помечает поле как первичный ключ сущности.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Аннотация JPA: определяет стратегию генерации значения для первичного ключа (обычно автоинкремент).
    @Column(name = "id")
    // Аннотация JPA: задаёт имя столбца, с которым будет сопоставлено данное поле.
    private Long id;

    @Column(name = "name")
    // Аннотация JPA: сопоставляет поле с колонкой "name" в таблице.
    private String name;

    @Column(name = "username")
    // Аннотация JPA: сопоставляет поле с колонкой "username" в таблице.
    private String username;

    @Column(name = "password")
    // Аннотация JPA: сопоставляет поле с колонкой "password" в таблице.
    private String password;

    @Column(name = "create_date")
    // Аннотация JPA: сопоставляет поле с колонкой "create_date". Используется тип LocalDateTime для хранения даты и времени.
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    // Аннотация JPA: указывает, что значение перечисления (enum) должно сохраняться в виде строки,
    // а не числового значения (ordinal), что улучшает читаемость и поддержку БД.
    @Column(name = "role")
    // Аннотация JPA: сопоставляет поле с колонкой "role" в таблице.
    private Role role;

    // Методы getters и setters, а также другие полезные методы (например, toString, equals, hashCode)
    // будут сгенерированы автоматически благодаря аннотации @Data от Lombok.
}
