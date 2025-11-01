# AI PC Build Suggestion - Phiên Bản Phôi

## 📝 Tổng quan

Đây là **phiên bản phôi/template** cho chức năng AI PC Build Suggestion. Hiện tại sử dụng **logic đơn giản dựa trên keyword**, chưa tích hợp AI API thực sự.

## 🎯 Chức năng hiện tại

### ✅ Đã có:
1. **Form nhập yêu cầu** - User nhập mô tả + ngân sách
2. **Phân tích keyword đơn giản** - Nhận diện: gaming, workstation, office
3. **Phân bổ ngân sách tự động** - Theo tỉ lệ % cho từng linh kiện
4. **Chọn linh kiện cơ bản** - Dựa trên performance level + budget
5. **Hiển thị kết quả** - Danh sách linh kiện + tổng giá
6. **Chấp nhận cấu hình** - Chuyển sang trang finish để hoàn tất

### ⏳ Chưa có (TODO):
- ❌ Tích hợp OpenAI/AI API thực sự
- ❌ Kiểm tra compatibility giữa linh kiện
- ❌ Xử lý khi không tìm thấy linh kiện phù hợp
- ❌ Điều chỉnh tiêu chí khi vượt ngân sách
- ❌ Nhiều lựa chọn thay thế
- ❌ Giải thích lý do chọn từng linh kiện

## 🚀 Cách sử dụng

### 1. Truy cập trang AI Suggest:
```
http://localhost:8081/build/ai/suggest
```

### 2. Nhập yêu cầu:
**Ví dụ:**
- "Máy tính chơi game cấu hình cao"
- "PC làm đồ họa và render 3D"
- "Máy văn phòng cơ bản"

**Ngân sách (tùy chọn):**
- VD: 30 (triệu VND)

### 3. Xem kết quả:
- Tiêu chí phân tích (use case, performance level, RAM/Storage)
- Danh sách 8 linh kiện được chọn
- Tổng giá trị

### 4. Chấp nhận hoặc thử lại:
- **Chấp nhận** → Chuyển sang `/build/finish`
- **Thử lại** → Nhập yêu cầu mới

## 📁 Cấu trúc code

```
src/main/java/com/example/PCOnlineShop/
├── dto/ai/
│   ├── AiPcBuildRequest.java          # DTO yêu cầu từ user
│   └── AiPcBuildCriteria.java         # DTO tiêu chí phân tích
│
├── service/ai/
│   ├── AiSuggestionService.java       # Phân tích yêu cầu → tiêu chí
│   └── PcBuilderService.java          # Tạo build từ tiêu chí
│
└── controller/ai/
    └── AiBuildController.java         # Xử lý request/response

src/main/resources/templates/build/
├── ai-suggest.html                    # Form nhập yêu cầu
└── ai-result.html                     # Hiển thị kết quả
```

## 🔧 Logic hiện tại

### 1. Phân tích keyword (AiSuggestionService)
```java
if (req.contains("game") || req.contains("chơi")) {
    → useCase = "gaming"
    → GPU = high, CPU = high, RAM = 16GB
}
else if (req.contains("đồ họa") || req.contains("render")) {
    → useCase = "workstation"
    → GPU = high, CPU = high, RAM = 32GB
}
else if (req.contains("văn phòng") || req.contains("office")) {
    → useCase = "office"
    → GPU = low, CPU = medium, RAM = 8GB
}
```

### 2. Phân bổ ngân sách
**Gaming:**
- GPU: 40%, CPU: 25%, MB: 12%, RAM: 10%
- Storage: 5%, PSU: 5%, Case: 2%, Cooling: 1%

**Workstation:**
- CPU: 35%, GPU: 30%, RAM: 15%, MB: 10%
- Storage: 5%, PSU: 3%, Case: 1%, Cooling: 1%

**Office:**
- CPU: 30%, MB: 20%, RAM: 15%, Storage: 15%
- GPU: 10%, PSU: 5%, Case: 3%, Cooling: 2%

### 3. Chọn linh kiện (PcBuilderService)
- **High performance** → Chọn linh kiện đắt nhất trong budget
- **Medium performance** → Chọn linh kiện ở giữa
- **Low performance** → Chọn linh kiện rẻ nhất

## 🔄 Roadmap phát triển

### Phase 1: Cải thiện logic (KHÔNG cần AI)
- [ ] Thêm kiểm tra compatibility (socket, form factor, TDP)
- [ ] Xử lý khi không tìm thấy linh kiện
- [ ] Tự động điều chỉnh budget nếu vượt quá
- [ ] Thêm nhiều use case (streaming, mining, ...)

### Phase 2: Tích hợp AI cơ bản
- [ ] Thêm OpenAI API dependency
- [ ] Cấu hình API key
- [ ] Tạo prompt template
- [ ] Parse JSON response từ AI
- [ ] Fallback về logic đơn giản nếu AI fail

### Phase 3: Nâng cao
- [ ] Nhiều lựa chọn thay thế cho mỗi linh kiện
- [ ] Giải thích lý do chọn
- [ ] So sánh nhiều cấu hình
- [ ] Lưu lịch sử suggest
- [ ] Rating & feedback

## 🧪 Test cases

### Test 1: Gaming build
```
Requirement: "Máy tính chơi game AAA"
Budget: 30 triệu
Expected: High-end GPU + CPU, 16GB RAM
```

### Test 2: Workstation
```
Requirement: "PC làm đồ họa 3D và render"
Budget: 40 triệu
Expected: High CPU + GPU, 32GB RAM
```

### Test 3: Office
```
Requirement: "Máy văn phòng"
Budget: 15 triệu
Expected: Medium CPU, Low GPU, 8GB RAM
```

### Test 4: Không có ngân sách
```
Requirement: "Build PC gaming"
Budget: (empty)
Expected: Chọn linh kiện medium-high
```

## ⚠️ Lưu ý quan trọng

1. **Đây chỉ là PHÔI** - Logic rất đ��n giản, chưa thông minh
2. **Không kiểm tra compatibility** - Có thể chọn linh kiện không tương thích
3. **Phụ thuộc database** - Cần có đủ sản phẩm trong DB
4. **Chưa xử lý edge cases** - VD: budget quá thấp, không có sản phẩm

## 🔌 Cách mở rộng sang AI thực

### Bước 1: Thêm dependency
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

### Bước 2: Cấu hình API key
```yaml
# application.yml
openai:
  api:
    key: ${OPENAI_API_KEY}
    url: https://api.openai.com/v1/chat/completions
    model: gpt-4o-mini
```

### Bước 3: Thay thế logic trong AiSuggestionService
```java
// Thay vì keyword matching
public AiPcBuildCriteria analyzeBuildRequirement(AiPcBuildRequest request) {
    // Call OpenAI API
    String aiResponse = callOpenAiApi(request);
    // Parse JSON response
    return parseToC riteria(aiResponse);
}
```

## 📞 Support

Nếu có câu hỏi hoặc cần hỗ trợ phát triển tiếp, vui lòng liên hệ team.

---

**Lưu ý:** Phiên bản này chỉ để **demo concept** và **chuẩn bị cấu trúc code**. Cần phát triển thêm để sử dụng production.

