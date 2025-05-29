import { Swiper, SwiperSlide } from "swiper/react";
import { Autoplay, Pagination, Navigation } from "swiper/modules";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";

export const Carousel: React.FC<{ img: string[] }> = ({ img }) => {
  return (
    <Swiper
      centeredSlides={true}
      autoplay={{
        delay: 2500,
        disableOnInteraction: false,
      }}
      pagination={{
        clickable: true,
      }}
      navigation={true}
      modules={[Autoplay, Pagination, Navigation]}
      className="Carousel h-screen"
    >
      {img.map((image, index) => (
        <SwiperSlide key={index}>
          <img src={image} alt="VAK" className="w-full h-full object-cover" />
        </SwiperSlide>
      ))}
    </Swiper>
  )
}