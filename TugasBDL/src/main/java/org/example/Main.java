package org.example;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        MongoClient client = MongoClients.create("mongodb+srv://mrevil24:fitra120103@cluster0.qagkqrg.mongodb.net/?retryWrites=true&w=majority");

        MongoDatabase db = client.getDatabase("TugasDBL");
        MongoCollection<Document> collection = db.getCollection("contact");

        int pilih ;
        Scanner input = new Scanner(System.in);
        System.out.println("Pilih Menu :");
        System.out.println("1. Tambah Kontak");
        System.out.println("2. Lihat Kontak");
        System.out.println("3. Edit Kontak");
        System.out.println("4. Hapus Kontak");
        System.out.println("5. Exit");
        System.out.print("Pilih : ");
        pilih = input.nextInt();
        input.nextLine();

        switch (pilih) {
            case 1:
                tambahKontak(collection);
                break;
            case 2:
                tampilkanSemuaKontak(collection);
                break;
            case 3:
                updateKontak(collection);
                break;
            case 4:
                hapusKontak(collection);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }
    }
    private static void tambahKontak( MongoCollection<Document> collection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama kontak: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan nomor telepon kontak: ");
        String nomorTelepon = scanner.nextLine();

        Document kontak = new Document("nama", nama)
                .append("nomorTelepon", nomorTelepon);

        collection.insertOne(kontak);
        System.out.println("Kontak berhasil ditambahkan.");

    }

    private static void tampilkanSemuaKontak(MongoCollection<Document> collection) {
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
        cursor.close();
    }

    private static void updateKontak(MongoCollection<Document> collection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama kontak yang ingin diupdate: ");
        String namaToUpdate = scanner.nextLine();

        System.out.print("Masukkan nomor telepon baru: ");
        String newNomorTelepon = scanner.nextLine();

        Document query = new Document("nama", namaToUpdate);
        Document update = new Document("$set", new Document("nomorTelepon", newNomorTelepon));

        collection.updateOne(query, update);
        System.out.println("Kontak berhasil diupdate.");
    }

    private static void hapusKontak(MongoCollection<Document> collection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama kontak yang ingin dihapus: ");
        String namaToDelete = scanner.nextLine();

        Document query = new Document("nama", namaToDelete);

        collection.deleteOne(query);
        System.out.println("Kontak berhasil dihapus.");
    }
}


