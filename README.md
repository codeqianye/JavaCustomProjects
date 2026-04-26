# JavaCustomProjects
Java日常自定义项目
## 一、E-Commerce-Center
Spring Cloud后台项目

## 二、EffectiveJava

effective java 书籍学习

## 三、AB策略实战项目——菜品搜索系统

### 项目说明

**场景**：餐厅 App 的菜品搜索，支持三种匹配策略：

- 精确名称匹配（exactName）：搜"宫保鸡丁"，精确找到
- 按分类匹配（byCategory）：搜"川菜"，找到所有川菜
- 模糊关键词匹配（fuzzy）：搜"鸡"，找到所有包含"鸡"的菜

**核心练习目标**：

1. 用模板方法写出 Processor 骨架，体验"流程固定，实现可变"
2. 用策略+工厂实现可插拔的 QueryBuilder，体验"新增不改旧代码"
3. 用配置驱动控制执行哪些策略，体验"改配置 = 改行为，不发版"
4. 亲手理解"同一个 Processor 类，不同 params，不同行为"

**技术栈**：Spring Boot，无需真实数据库，内存数据模拟搜索引擎。