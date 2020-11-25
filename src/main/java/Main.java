import Entity.FilmsEntity;
import Entity.BookingsEntity;
import Entity.TicketsEntity;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import javax.swing.*;

import java.util.List;
import java.util.Scanner;

class DataAccess {
    public static SessionFactory sessionFactory = null;

    public static SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = null;
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources sources = new MetadataSources(registry);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }
}



public class Main extends JFrame {

    public static void main(String[] args) {
        char sym = ' ';
        boolean mark = false;
        while (!mark) {
            System.out.println("Приложение CRUD с помощью Hibernate");
            menu();
            try {
                int temp = System.in.read();
                sym = (char)temp;
                System.in.skip(System.in.available());
                Transaction transaction = null;
                switch (sym) {
                    case '0':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaQuery<FilmsEntity> criteriaSelect = criteriaBuilder.createQuery(FilmsEntity.class);
                            Root<FilmsEntity> root = criteriaSelect.from(FilmsEntity.class);
                            criteriaSelect.orderBy(criteriaBuilder.asc(root.get("id")));
                            criteriaSelect.select(root);
                            Query query = session.createQuery(criteriaSelect);
                            List<FilmsEntity> list = query.list();
                            System.out.println("films\n\nid\tname");
                            for (FilmsEntity s : list) {
                                System.out.println(s.toString());
                            }
                            System.out.println();
                        } catch (Exception e) {
                            System.out.println("Ошибка SELECT\nПодробнее: " + e.getMessage() + "\n\n");
                        }
                        break;
                    case '1':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите название: ");
                            Scanner sc = new Scanner(System.in);
                            String str = sc.nextLine();
                            FilmsEntity fs = new FilmsEntity();
                            fs.setName(str);
                            transaction = session.beginTransaction();
                            session.save(fs);
                            transaction.commit();
                            System.out.println("Строка успешно добавлена\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка INSERT\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '2':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД редактируемой записи (для выхода введите отрицательный ИД): ");
                            Scanner sc = new Scanner(System.in);
                            int id = sc.nextInt();
                            if (id < 0) break;
                            System.out.print("\nВведите новое название: ");
                            Scanner sc2 = new Scanner(System.in);
                            String str = sc2.nextLine();
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaUpdate<FilmsEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(FilmsEntity.class);
                            Root<FilmsEntity> root = criteriaUpdate.from(FilmsEntity.class);
                            criteriaUpdate.set("name", str);
                            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
                            transaction = session.beginTransaction();
                            Query query = session.createQuery(criteriaUpdate);
                            int result = query.executeUpdate();
                            transaction.commit();
                            System.out.println("Обновлено строк: " + result + "\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка UPDATE\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '3':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД удаляемой записи (для выхода введите отрицательный ИД): ");
                            Scanner sc = new Scanner(System.in);
                            int id = sc.nextInt();
                            if (id < 0) break;
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaDelete<FilmsEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(FilmsEntity.class);
                            Root<FilmsEntity> root = criteriaDelete.from(FilmsEntity.class);
                            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
                            transaction = session.beginTransaction();
                            Query query = session.createQuery(criteriaDelete);
                            int result = query.executeUpdate();
                            transaction.commit();
                            System.out.println("Удалено строк: " + result + "\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка DELETE\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '4':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaQuery<BookingsEntity> criteriaSelect = criteriaBuilder.createQuery(BookingsEntity.class);
                            Root<BookingsEntity> root = criteriaSelect.from(BookingsEntity.class);
                            criteriaSelect.orderBy(criteriaBuilder.asc(root.get("id")));
                            criteriaSelect.select(root);
                            Query query = session.createQuery(criteriaSelect);
                            List<BookingsEntity> gwh = query.list();
                            System.out.println("bookings\n\nid\ttid");
                            for (BookingsEntity s : gwh) {
                                System.out.println(s.toString());
                            }
                            System.out.println();
                        } catch (Exception e) {
                            System.out.println("Ошибка SELECT\nПодробнее: " + e.getMessage() + "\n\n");
                        }
                        break;
                    case '5':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД билета (для выхода введите отрицательный ИД): ");
                            Scanner sc2 = new Scanner(System.in);
                            int wId = sc2.nextInt();
                            if (wId < 0) break;
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaQuery<TicketsEntity> criteriaSelect2 = criteriaBuilder.createQuery(TicketsEntity.class);
                            Root<TicketsEntity> root2 = criteriaSelect2.from(TicketsEntity.class);
                            criteriaSelect2.where(criteriaBuilder.equal(root2.get("id"), wId));
                            criteriaSelect2.select(root2);
                            Query query2 = session.createQuery(criteriaSelect2);
                            List<TicketsEntity> list = query2.list();
                            BookingsEntity gwh = new BookingsEntity();
                            gwh.setTicket(list.get(0));
                            transaction = session.beginTransaction();
                            session.save(gwh);
                            transaction.commit();
                            System.out.println("Строка успешно добавлена\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка INSERT\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '6':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД редактируемой записи (для выхода введите отрицательный ИД): ");
                            Scanner sc = new Scanner(System.in);
                            int id = sc.nextInt();
                            if (id < 0) break;
                            System.out.print("\nВведите новый ИД билета (если хотите оставить введите отрицательный ИД): ");
                            Scanner sc2 = new Scanner(System.in);
                            int gId = sc2.nextInt();
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaQuery<TicketsEntity> criteriaSelect2 = criteriaBuilder.createQuery(TicketsEntity.class);
                            Root<TicketsEntity> root2 = criteriaSelect2.from(TicketsEntity.class);
                            criteriaSelect2.where(criteriaBuilder.equal(root2.get("id"), gId));
                            criteriaSelect2.select(root2);
                            Query query2 = session.createQuery(criteriaSelect2);
                            List<TicketsEntity> nw = query2.list();
                            Query query3;
                            if (gId < 0) {
                                System.out.println("Обновлено строк: 0\n\n");
                                break;
                            } else {
                                CriteriaUpdate<BookingsEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(BookingsEntity.class);
                                Root<BookingsEntity> root = criteriaUpdate.from(BookingsEntity.class);
                                criteriaUpdate.set("warehousesByIdWh", nw.get(0));
                                criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
                                query3 = session.createQuery(criteriaUpdate);
                            }

                            transaction = session.beginTransaction();
                            int result = query3.executeUpdate();
                            transaction.commit();
                            System.out.println("Обновлено строк: " + result + "\n\n");

                        } catch (Exception e) {
                            System.out.println("Ошибка UPDATE\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '7':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД удаляемой записи (для выхода введите отрицательный ИД): ");
                            Scanner sc = new Scanner(System.in);
                            int id = sc.nextInt();
                            if (id < 0) break;
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaDelete<BookingsEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(BookingsEntity.class);
                            Root<BookingsEntity> root = criteriaDelete.from(BookingsEntity.class);
                            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
                            transaction = session.beginTransaction();
                            Query query = session.createQuery(criteriaDelete);
                            int result = query.executeUpdate();
                            transaction.commit();
                            System.out.println("Удалено строк: " + result + "\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка DELETE\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '8':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaQuery<TicketsEntity> criteriaSelect = criteriaBuilder.createQuery(TicketsEntity.class);
                            Root<TicketsEntity> root = criteriaSelect.from(TicketsEntity.class);
                            criteriaSelect.orderBy(criteriaBuilder.asc(root.get("id")));
                            criteriaSelect.select(root);
                            Query query = session.createQuery(criteriaSelect);
                            List<TicketsEntity> list = query.list();
                            System.out.println("tickets\n\nid\tname");
                            for (TicketsEntity s : list) {
                                System.out.println(s.toString());
                            }
                            System.out.println();
                        } catch (Exception e) {
                            System.out.println("Ошибка SELECT\nПодробнее: " + e.getMessage());
                        }
                        break;
                    case '9':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД фильма (для выхода введите отрицательный ИД): ");
                            Scanner sc2 = new Scanner(System.in);
                            int wId = sc2.nextInt();
                            if (wId < 0) break;
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaQuery<FilmsEntity> criteriaSelect2 = criteriaBuilder.createQuery(FilmsEntity.class);
                            Root<FilmsEntity> root2 = criteriaSelect2.from(FilmsEntity.class);
                            criteriaSelect2.where(criteriaBuilder.equal(root2.get("id"), wId));
                            criteriaSelect2.select(root2);
                            Query query2 = session.createQuery(criteriaSelect2);
                            List<FilmsEntity> warehouses = query2.list();
                            TicketsEntity gwh = new TicketsEntity();
                            gwh.setFilm(warehouses.get(0));
                            transaction = session.beginTransaction();
                            session.save(gwh);
                            transaction.commit();
                            System.out.println("Строка успешно добавлена\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка INSERT\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '-':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД редактируемой записи (для выхода введите отрицательный ИД): ");
                            Scanner sc = new Scanner(System.in);
                            int id = sc.nextInt();
                            if (id < 0) break;
                            System.out.print("\nВведите новый ИД фильма (если хотите оставить введите отрицательный ИД): ");
                            Scanner sc2 = new Scanner(System.in);
                            int gId = sc2.nextInt();
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaQuery<FilmsEntity> criteriaSelect2 = criteriaBuilder.createQuery(FilmsEntity.class);
                            Root<FilmsEntity> root2 = criteriaSelect2.from(FilmsEntity.class);
                            criteriaSelect2.where(criteriaBuilder.equal(root2.get("id"), gId));
                            criteriaSelect2.select(root2);
                            Query query2 = session.createQuery(criteriaSelect2);
                            List<FilmsEntity> nw = query2.list();
                            Query query3;
                            CriteriaUpdate<TicketsEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(TicketsEntity.class);
                            Root<TicketsEntity> root = criteriaUpdate.from(TicketsEntity.class);
                            criteriaUpdate.set("goodsMainByIdGd", nw.get(0));
                            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
                            query3 = session.createQuery(criteriaUpdate);
                            transaction = session.beginTransaction();
                            int result = query3.executeUpdate();
                            transaction.commit();
                            System.out.println("Обновлено строк: " + result + "\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка UPDATE\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '=':
                        try (Session session = DataAccess.createSessionFactory().openSession()) {
                            System.out.print("\nВведите ИД удаляемой записи (для выхода введите отрицательный ИД): ");
                            Scanner sc = new Scanner(System.in);
                            int id = sc.nextInt();
                            if (id < 0) break;
                            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                            CriteriaDelete<TicketsEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(TicketsEntity.class);
                            Root<TicketsEntity> root = criteriaDelete.from(TicketsEntity.class);
                            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
                            transaction = session.beginTransaction();
                            Query query = session.createQuery(criteriaDelete);
                            int result = query.executeUpdate();
                            transaction.commit();
                            System.out.println("Удалено строк: " + result + "\n\n");
                        } catch (Exception e) {
                            System.out.println("Ошибка DELETE\nПодробнее: " + e.getMessage() + "\n\n");
                            transaction.rollback();
                        }
                        break;
                    case '\': mark = true; break;
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage() + "\n\n");
            }
        }
    }
    public static void menu(){
        System.out.println("Варианты взаимодействия:");
        System.out.println("Таблица 'Фильмы':");
        System.out.println("0.\Отобразить.");
        System.out.println("1.\tВставить.");
        System.out.println("2.\tОбновить.");
        System.out.println("3.\tУдалить.");
        System.out.println("Таблица 'Бронирования':");
        System.out.println("4.\tОтобразить.");
        System.out.println("5.\tВставить.");
        System.out.println("6.\tОбновить.");
        System.out.println("7.\tУдалить.");
        System.out.println("Таблица 'Билеты':");
        System.out.println("8.\tОтобразить.");
        System.out.println("9.\tВставить.");
        System.out.println("-.\tОбновить.");
        System.out.println("=.\tУдалить.\n");
        System.out.println("\.\tВыход.");
        System.out.print("Введите номер пункта и нажмите Enter: ");
    }
}