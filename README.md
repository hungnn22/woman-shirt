# woman-shirt
1. Chỉ làm việc trên nhánh dev. 
2. Khi code 1 feature mới cũng checkout từ dev ra
3. Chỉ tạo Pull requests đến nhánh dev
4. Khi push lên thì ignore folder .idea

# Cách run prj để có db 
1. Xóa db ở local
2. Vào class MasterServerApplication uncomment đoan code

try {
   new BatchSeeder().seed();
} catch (NoSuchAlgorithmException e) {
   e.printStackTrace();
}
3. sau khi project run thành công thì comment lại đoạn code bước 2