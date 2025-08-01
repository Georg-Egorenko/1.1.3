package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoHibernateImpl();

    @Override
    public void createUsersTable() {
        try {
            userDao.createUsersTable();
            System.out.println("Таблица пользователей успешно создана (Hibernate)");
        } catch (Exception e) {
            System.err.println("Ошибка при создании таблицы (Hibernate): " + e.getMessage());
            throw new RuntimeException("Failed to create table", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            userDao.dropUsersTable();
            System.out.println("Таблица пользователей успешно удалена (Hibernate)");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении таблицы (Hibernate): " + e.getMessage());
            throw new RuntimeException("Failed to drop table", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            userDao.saveUser(name, lastName, age);
            System.out.printf("User с именем - %s %s (возраст %d) добавлен в базу данных (Hibernate)%n",
                    name, lastName, age);
        } catch (Exception e) {
            System.err.printf("Ошибка при добавлении пользователя %s (Hibernate): %s%n",
                    name, e.getMessage());
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            userDao.removeUserById(id);
            System.out.println("Пользователь с ID " + id + " удален (Hibernate)");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя (Hibernate): " + e.getMessage());
            throw new RuntimeException("Failed to remove user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            List<User> users = userDao.getAllUsers();
            System.out.println("Получен список пользователей (Hibernate):");
            users.forEach(user -> System.out.println("\t" + user));
            return users;
        } catch (Exception e) {
            System.err.println("Ошибка при получении пользователей (Hibernate): " + e.getMessage());
            throw new RuntimeException("Failed to get users", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try {
            userDao.cleanUsersTable();
            System.out.println("Таблица пользователей очищена (Hibernate)");
        } catch (Exception e) {
            System.err.println("Ошибка при очистке таблицы (Hibernate): " + e.getMessage());
            throw new RuntimeException("Failed to clean table", e);
        }
    }
}