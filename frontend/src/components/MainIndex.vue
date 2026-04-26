<template>
  <div class="container">
    <div class="brand-bar">
      <div class="logo">GLOBE<span>｜好物集</span></div>
      <div class="tagline">EDITOR'S PICK · 今日全球灵感</div>
    </div>

    <!-- 上部分：定时滑动窗口 -->
    <div class="hero-slider-section">
      <div class="slider-container" @mouseenter="stopAutoSlide" @mouseleave="startAutoSlide">
        <div class="slider-track" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
          <div
              v-for="(product, idx) in products"
              :key="product.id"
              class="slide"
              :style="{ backgroundImage: `url(${product.image})` }"
          >
            <div class="slide-overlay">
              <div class="slide-category">{{ product.category }}</div>
              <div class="slide-title">{{ product.title }}</div>
              <div class="slide-desc">{{ product.desc }}</div>
            </div>
          </div>
        </div>
        <div class="slider-dots">
          <div
              v-for="(product, idx) in products"
              :key="'dot-' + product.id"
              class="dot"
              :class="{ active: idx === currentIndex }"
              @click="goToSlide(idx)"
          ></div>
        </div>
      </div>
    </div>

    <!-- 下半部分：全球好物网格 -->
    <div class="global-section">
      <div class="section-header">
        <h2>· 全球甄选 ·</h2>
        <p>今日灵感 | 同款好物</p>
      </div>
      <div class="goods-grid">
        <div v-for="product in products" :key="product.id" class="product-card">
          <div class="card-img" :style="{ backgroundImage: `url(${product.cardImg})` }"></div>
          <div class="card-info">
            <div class="product-brand">{{ product.brand }}</div>
            <div class="product-name">{{ product.title }}</div>
            <div class="product-price">{{ product.price }}</div>
          </div>
        </div>
      </div>
    </div>
    <footer>
      © 全球好物巡礼 — 简约生活，漫游世界选品
    </footer>
  </div>
</template>

<script>
export default {
  name: 'GlobalGoods',
  data() {
    return {
      currentIndex: 0,
      intervalId: null,
      slideInterval: 2000,
      // 商品数据 + 图片地址
      products: [
        {
          id: 1,
          category: "北欧家居",
          title: "极简橡木茶几",
          desc: "瑞典设计，自然曲线，低饱和度空间灵魂",
          price: "¥ 2,499",
          brand: "ARTEK",
          image: "https://images.unsplash.com/photo-1533090481720-856c6e3c1fdc?w=1200&h=800&fit=crop",
          cardImg: "https://images.unsplash.com/photo-1533090481720-856c6e3c1fdc?w=400&h=400&fit=crop"
        },
        {
          id: 2,
          category: "日式器物",
          title: "手作锤纹玻璃杯",
          desc: "每一只都是独一无二的涟漪光影",
          price: "¥ 189",
          brand: "TSUKI",
          image: "https://images.unsplash.com/photo-1577805947697-89e18249d767?w=1200&h=800&fit=crop",
          cardImg: "https://images.unsplash.com/photo-1577805947697-89e18249d767?w=400&h=400&fit=crop"
        },
        {
          id: 3,
          category: "法式香氛",
          title: "无花果叶香薰蜡烛",
          desc: "南法格拉斯调香，静谧木质调",
          price: "¥ 380",
          brand: "LE BOTANISTE",
          image: "https://images.unsplash.com/photo-1602874801007-bd36f2d95eb6?w=1200&h=800&fit=crop",
          cardImg: "https://images.unsplash.com/photo-1602874801007-bd36f2d95eb6?w=400&h=400&fit=crop"
        },
        {
          id: 4,
          category: "意式皮具",
          title: "植鞣革邮差包",
          desc: "托斯卡纳传统工艺，越用越有质感",
          price: "¥ 3,280",
          brand: "CUOIO",
          image: "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=1200&h=800&fit=crop",
          cardImg: "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=400&h=400&fit=crop"
        },
        {
          id: 5,
          category: "瑞士经典",
          title: "超薄腕表·墨韵",
          desc: "极简表盘，蓝宝石玻璃，永恒设计",
          price: "¥ 8,990",
          brand: "HORLOGE",
          image: "https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=1200&h=800&fit=crop",
          cardImg: "https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=400&h=400&fit=crop"
        }
      ]
    }
  },
  mounted() {
    this.startAutoSlide()
    // 窗口resize时修正轮播位置
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    this.stopAutoSlide()
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    goToSlide(index) {
      this.currentIndex = index
    },
    nextSlide() {
      let next = this.currentIndex + 1
      if (next >= this.products.length) next = 0
      this.currentIndex = next
    },
    startAutoSlide() {
      if (this.intervalId) clearInterval(this.intervalId)
      this.intervalId = setInterval(() => {
        this.nextSlide()
      }, this.slideInterval)
    },
    stopAutoSlide() {
      if (this.intervalId) {
        clearInterval(this.intervalId)
        this.intervalId = null
      }
    },
    handleResize() {
      // 简单触发视图更新，transform会在Vue响应式下自动保持
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  background-color: #f7f7f7;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  color: #1e1e2a;
}

.container {
  max-width: 1440px;
  margin: 0 auto;
  padding: 2rem 2rem 4rem;
}

.brand-bar {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 2.5rem;
  flex-wrap: wrap;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  padding-bottom: 1rem;
}

.logo {
  font-size: 1.5rem;
  font-weight: 600;
  letter-spacing: -0.3px;
  background: linear-gradient(135deg, #1e1e2a 0%, #3a3a4a 100%);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

.logo span {
  font-weight: 300;
  color: #a0a0b0;
  background: none;
  -webkit-background-clip: unset;
  background-clip: unset;
}

.tagline {
  font-size: 0.85rem;
  color: #888;
  letter-spacing: 0.3px;
}

.hero-slider-section {
  margin-bottom: 4rem;
  border-radius: 28px;
  overflow: hidden;
  box-shadow: 0 20px 35px -12px rgba(0, 0, 0, 0.08);
  background: #fff;
}

.slider-container {
  position: relative;
  width: 100%;
  aspect-ratio: 16 / 9;
  background: #eaeef2;
  overflow: hidden;
}

.slider-track {
  display: flex;
  width: 100%;
  height: 100%;
  transition: transform 0.5s cubic-bezier(0.2, 0.9, 0.4, 1.1);
}

.slide {
  flex: 0 0 100%;
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: flex-end;
  justify-content: flex-start;
}

.slide-overlay {
  background: linear-gradient(to top, rgba(0,0,0,0.5), transparent);
  width: 100%;
  padding: 2rem 2.5rem;
  color: white;
}

.slide-category {
  font-size: 0.8rem;
  letter-spacing: 2px;
  text-transform: uppercase;
  font-weight: 400;
  background: rgba(255,255,255,0.2);
  display: inline-block;
  padding: 0.2rem 0.8rem;
  border-radius: 30px;
  backdrop-filter: blur(4px);
  margin-bottom: 0.75rem;
}

.slide-title {
  font-size: 2rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.slide-desc {
  font-size: 0.9rem;
  opacity: 0.9;
  max-width: 70%;
}

.slider-dots {
  position: absolute;
  bottom: 1.2rem;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  gap: 10px;
  z-index: 5;
}

.dot {
  width: 8px;
  height: 8px;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 10px;
  transition: all 0.2s;
  cursor: pointer;
}

.dot.active {
  width: 28px;
  background-color: white;
}

.global-section {
  margin-top: 1rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 2rem;
}

.section-header h2 {
  font-weight: 500;
  font-size: 1.6rem;
  color: #1f1f2b;
}

.section-header p {
  color: #777;
  font-size: 0.85rem;
}

.goods-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1.8rem;
}

.product-card {
  background: white;
  border-radius: 20px;
  overflow: hidden;
  transition: all 0.25s ease;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.02);
  border: 1px solid rgba(0, 0, 0, 0.03);
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 25px -12px rgba(0, 0, 0, 0.1);
  border-color: rgba(0, 0, 0, 0.05);
}

.card-img {
  aspect-ratio: 1 / 1;
  background-size: cover;
  background-position: center;
  transition: transform 0.4s;
}

.product-card:hover .card-img {
  transform: scale(1.02);
}

.card-info {
  padding: 1.2rem 1rem 1.4rem;
}

.product-brand {
  font-size: 0.7rem;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: #aaa;
  margin-bottom: 0.4rem;
}

.product-name {
  font-weight: 600;
  font-size: 1rem;
  margin-bottom: 0.5rem;
  color: #1e1e2a;
}

.product-price {
  font-weight: 500;
  color: #2c6e2f;
  font-size: 1rem;
}

footer {
  margin-top: 4rem;
  text-align: center;
  font-size: 0.75rem;
  color: #aaa;
  border-top: 1px solid #eaeef2;
  padding-top: 2rem;
}

@media (max-width: 1024px) {
  .container { padding: 1.5rem; }
  .goods-grid { gap: 1.2rem; }
}

@media (max-width: 820px) {
  .goods-grid { grid-template-columns: repeat(2, 1fr); }
  .slide-title { font-size: 1.4rem; }
  .slide-desc { max-width: 90%; }
}

@media (max-width: 540px) {
  .goods-grid { grid-template-columns: 1fr; }
  .brand-bar { flex-direction: column; gap: 8px; }
}
</style>