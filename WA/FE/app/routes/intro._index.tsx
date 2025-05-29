import { Link } from "@remix-run/react";

export default function Intro() {
	const lh = [
		{
			icon: "fa-light fa-phone",
			link: "tel:+86908000888",
			text: "0908000888",
		},
		{
			icon: "fa-light fa-envelope",
			link: "mailto:vak@gmail.com",
			text: "vak@gmail.com",
		},
		{
			icon: "fa-brands fa-square-facebook",
			link: "https://www.facebook.com",
			text: "VAK",
		},
	];
	const intro = [
		"Step into OOP, a dynamic hub where the pulse of modern technology beats strong. We are a dedicated retailer offering a diverse and carefully chosen collection of tech products designed to enhance and streamline your digital lifestyle. While our inventory spans a broad spectrum of innovative gadgets and electronic solutions, at the heart of OOP lies a deep commitment to the foundational elements of computing. We specialize in providing an extensive array of high-performance computer components, the very building blocks that empower the digital experiences we rely on daily.",
		"Within the walls of OOP, both physical and virtual, you'll find a meticulously curated selection of the essential hardware that drives the modern computer. We understand that the performance and reliability of any digital system are intrinsically linked to the quality of its core components. Therefore, we have dedicated ourselves to sourcing and offering a wide variety of these crucial elements, ensuring that our customers have access to the very best in processing power, memory solutions, and storage technologies.",
		"Our focus on computer parts stems from a belief that a strong foundation is paramount. Whether you are a seasoned PC builder crafting a bespoke machine, a gamer seeking peak performance, a creative professional demanding seamless workflows, or simply someone looking to upgrade and extend the life of their current system, OOP provides the essential components you need. We offer a diverse range of options to cater to various needs and budgets, all while maintaining a commitment to quality and performance.",
		"At OOP, we strive to be more than just a retailer. We aim to be a knowledgeable resource, guiding you through the intricacies of computer hardware and helping you select the perfect components for your specific requirements. Our team is passionate about technology and dedicated to providing the expertise and support you need to make informed decisions. We believe in empowering our customers with access to the fundamental elements that drive their digital world, ensuring they have the power to build, upgrade, and optimize their systems with confidence. Welcome to OOP, where the core of computing meets quality and expertise.",
	];
	return (
		<main className="w-4/5 min-h-screen pt-12 m-auto">
			<img
				src="/app/IMG/VAK.png"
				alt="VAK"
				className="w-1/12 border-b-2 border-teal-500 py-10 m-auto"
				loading="lazy"
			/>
			{intro.map((text, index) => (
				<p key={index} className="text-xl py-2">
					{text}
				</p>
			))}
			<section className="border-y-2 border-black py-10 mt-10 flex">
				<section className="w-1/2 border-r border-black">
					<address className="space-y-2">
						{lh.map((ct) => (
							<div key={ct.text} className="flex items-center">
								<i className={`${ct.icon} mx-2 text-xl`} />
								<Link to={ct.link} className="text-xl">
									{ct.text}
								</Link>
							</div>
						))}
					</address>
				</section>
				<section className="w-1/2 border-l border-black flex justify-center items-center flex-col">
					<p className="text-2xl">VAK Company Limited</p>
					<div className="mt-4 flex items-center">
						<i className="fa-light fa-location-dot text-xl mx-2"></i>
						<Link
							to="https://maps.app.goo.gl/A7TdvgX8upqxfVJt8"
							rel="noopener noreferrer"
							target="_blank"
							className="text-xl"
						>
							Nguyen Trac - Yen Nghia - Ha Dong - Ha Noi
						</Link>
					</div>
				</section>
			</section>
			<section>
				<iframe
					src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3725.74853797683!2d105.7486864!3d20.962611199999998!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x313452efff394ce3%3A0x391a39d4325be464!2sPhenikaa%20University!5e0!3m2!1sen!2s!4v1746208005183!5m2!1sen!2s"
					className="w-full border-2 border-black mb-10"
					loading="lazy"
				></iframe>
			</section>
		</main>
	)
}