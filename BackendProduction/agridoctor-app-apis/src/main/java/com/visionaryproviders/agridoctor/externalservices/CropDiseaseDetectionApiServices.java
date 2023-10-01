package com.visionaryproviders.agridoctor.externalservices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;


import com.visionaryproviders.agridoctor.config.RestTemplateConfig;
import com.visionaryproviders.agridoctor.payloads.CropDiseaseDetectionRequestDto;
import com.visionaryproviders.agridoctor.payloads.CropDiseaseDetectionResponse;
import com.visionaryproviders.agridoctor.payloads.CropRecommendationResponse;
import com.visionaryproviders.agridoctor.services.UserServices;

@CrossOrigin(origins = "https://therap-javafest-agridoctor.vercel.app")
@Service
public class CropDiseaseDetectionApiServices {

	@Value("${crop_disease_Detection.api.url}")
    private String cropDiseaseDetectionApiUrl;
	
	
	@Value("${project.image}")
	private String path;
	//private RestTemplateConfig restTemplateConfig;

	@Autowired
	private UserServices userServices;
	
	@Autowired
    private RestTemplate restTemplate ;
	
	
	public CropDiseaseDetectionResponse callCropDiseaseDetectionApi(@RequestParam("input_data") MultipartFile file, @RequestParam("uid") Integer uid) throws IOException  {
		
		
		
		CropDiseaseDetectionResponse cropDiseaseDetectionResponse = new CropDiseaseDetectionResponse();
    	if(!userServices.subtractWalletUser(uid)) {
    		cropDiseaseDetectionResponse.setMessage("No currency left. Cannot Provide Service.");
    		cropDiseaseDetectionResponse.setWallet(userServices.getWalletUser(uid));
    		    return cropDiseaseDetectionResponse;
    	}
		
		
		
		
		
		
		
		// Create headers for the multipart request
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	   
	    
	    
	 // Create a MultiValueMap to represent the form data
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        
        // Add the image file with the key name "input_data"
        formData.add("input_data", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        // Create a request entity with the MultiValueMap as the body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

	    
	    
	    
	
        
        
	 // Make the POST request to the external API
        ResponseEntity<CropDiseaseDetectionResponse> responseEntity =
                restTemplate.postForEntity(cropDiseaseDetectionApiUrl, requestEntity, CropDiseaseDetectionResponse.class);
	    
	   
        
        
        String prediction = responseEntity.getBody().getPrediction();
        
        
        if(prediction.equals("Apple___Apple_scab")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Apple");
        	responseEntity.getBody().setCropNameBn("আপেল");
        	
        	responseEntity.getBody().setDiseaseNameEn("Apple Scab");
        	responseEntity.getBody().setDiseaseNameBn("আপেল স্ক্যাব");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/> 1. Apple scab overwinters primarily in fallen leaves and in the soil. Disease development is favored by wet, cool weather that generally occurs in spring and early summer.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. Fungal spores are carried by wind, rain or splashing water from the ground to flowers, leaves or fruit. During damp or rainy periods, newly opening apple leaves are extremely susceptible to infection. The longer the leaves remain wet, the more severe the infection will be. Apple scab spreads rapidly between 55-75 degrees Fahrenheit.");
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. আপেল স্ক্যাব প্রধানত পতিত পাতায় এবং মাটিতে শীতকালে পড়ে। ভেজা, শীতল আবহাওয়ায় রোগের বিকাশ ঘটতে পারে যা সাধারণত বসন্ত এবং গ্রীষ্মের শুরুতে ঘটে।\\r\\n\"\r\n"
        			+ "        + \"\\r\\n\"\r\n"
        			+ "        + \" <br/> 2. ছত্রাকের স্পোর বাতাস, বৃষ্টি বা ছিটানো জলের মাধ্যমে মাটি থেকে ফুল, পাতা বা ফলের মধ্যে বাহিত হয়। স্যাঁতসেঁতে বা বর্ষার সময়, নতুন খোলা আপেলের পাতাগুলি সংক্রমণের জন্য অত্যন্ত সংবেদনশীল। পাতা যত বেশি সময় থাকে। ভেজা, সংক্রমণ আরও গুরুতর হবে।আপেল স্ক্যাব 55-75 ডিগ্রি ফারেনহাইটের মধ্যে দ্রুত ছড়িয়ে পড়ে।");
        	
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/>1. Choose resistant varieties when possible.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Rake under trees and destroy infected leaves to reduce the number of fungal spores available to start the disease cycle over again next spring\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Water in the evening or early morning hours (avoid overhead irrigation) to give the leaves time to dry out before infection can occur.\r\n"
        			+ "        <br/>4. Spread a 3- to 6-inch layer of compost under trees, keeping it away from the trunk, to cover soil and prevent splash dispersal of the fungal spores.\"\"\",\r\n"
        			);
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. সম্ভব হলে প্রতিরোধী জাত বেছে নিন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. পরবর্তী বসন্তে আবার রোগ চক্র শুরু করার জন্য উপলব্ধ ছত্রাকের বীজের সংখ্যা কমাতে গাছের নিচে রেক করুন এবং সংক্রামিত পাতা ধ্বংস করুন\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. সন্ধ্যায় বা ভোরে পানি দিন (ওভারহেড সেচ এড়িয়ে চলুন) যাতে সংক্রমণ হওয়ার আগে পাতা শুকিয়ে যায়।\r\n"
        			+ "        <br/>৪. গাছের নিচে 3 থেকে 6-ইঞ্চি কম্পোস্টের স্তর ছড়িয়ে দিন, এটি কাণ্ড থেকে দূরে রেখে, মাটি ঢেকে রাখুন এবং ছত্রাকের স্পোর ছড়িয়ে পড়া রোধ করুন।");
        	
        }     else if(prediction.equals("Apple___Black_rot")) {
        	
  
        	
        	responseEntity.getBody().setCropNameEn("Apple");
        	responseEntity.getBody().setCropNameBn("আপেল");
        	
        	responseEntity.getBody().setDiseaseNameEn("Apple Black Rot");
        	responseEntity.getBody().setDiseaseNameBn("আপেল কালো পচা");
        	
        	
        	responseEntity.getBody().setCauseOfDiseaseEn("<br/><br/>Black rot is caused by the fungus Diplodia seriata (syn Botryosphaeria obtusa).The fungus can infect dead tissue as well as living trunks, branches, leaves and fruits. In wet weather, spores are released from these infections and spread by wind or splashing water. The fungus infects leaves and fruit through natural openings or minor wounds.");
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/>ডিপ্লোডিয়া সিরিয়াটা (সিন বোট্রিওসফেরিয়া ওবটুসা) ছত্রাকের কারণে কালো পচা হয়। ছত্রাক মৃত টিস্যু এবং সেইসাথে জীবন্ত কাণ্ড, শাখা, পাতা এবং ফলকে সংক্রমিত করতে পারে। আর্দ্র আবহাওয়ায়, এই সংক্রমণ থেকে স্পোর মুক্ত হয় এবং বাতাস বা স্প্ল্যাশিং জলের মাধ্যমে ছড়িয়ে পড়ে। ছত্রাক প্রাকৃতিক ছিদ্র বা ছোট ক্ষতের মাধ্যমে পাতা ও ফলকে সংক্রমিত করে।");
        	
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/>1. Prune out dead or diseased branches. <br/>2. Remove infected plant material from the area. <br/>5. Be sure to remove the stumps of any apple trees you cut down. Dead stumps can be a source of spores.");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. মরা বা রোগাক্রান্ত শাখাগুলি ছেঁটে ফেলুন৷ <br/>2. এলাকা থেকে সংক্রামিত উদ্ভিদের উপাদানগুলি সরান৷ <br/>5. আপনি যে কোনো আপেল গাছ কেটে ফেলেছেন তার স্টাম্প অপসারণ করতে ভুলবেন না৷ মৃত স্টাম্প স্পোরের উৎস হতে পারে।");
        	
        	
        }     else if(prediction.equals("Apple___Cedar_apple_rust")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Apple");
        	responseEntity.getBody().setCropNameBn("আপেল");
        	
        	responseEntity.getBody().setDiseaseNameEn("Cedar Apple Rust");
        	responseEntity.getBody().setDiseaseNameBn("সিডার আপেল মরিচা");
        	
        	
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/>Cedar apple rust (Gymnosporangium juniperi-virginianae) is a fungal disease that depends on two species to spread and develop. It spends a portion of its two-year life cycle on Eastern red cedar (Juniperus virginiana). The pathogen’s spores develop in late fall on the juniper as a reddish brown gall on young branches of the trees.");
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/>সিডার আপেল মরিচা (Gymnosporangium juniperi-virginianae) একটি ছত্রাকজনিত রোগ যা বিস্তার ও বিকাশের জন্য দুটি প্রজাতির উপর নির্ভর করে। এটি তার দুই বছরের জীবনচক্রের একটি অংশ পূর্ব লাল সিডারে (জুনিপেরাস ভার্জিনিয়ানা) ব্যয় করে। প্যাথোজেনের স্পোরগুলো শরতের শেষের দিকে গাছের কচি ডালে লালচে বাদামী পিত্তের মতো বিকশিত হয়।");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Since the juniper galls are the source of the spores that infect the apple trees, cutting them is a sound strategy if there aren’t too many of them.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. While the spores can travel for miles, most of the ones that could infect your tree are within a few hundred feet.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. The best way to do this is to prune the branches about 4-6 inches below the galls.");
        	
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. যেহেতু জুনিপার গলগুলি সেই স্পোরগুলির উত্স যা আপেল গাছগুলিকে সংক্রামিত করে, সেগুলি যদি খুব বেশি না থাকে তবে সেগুলি কাটা একটি ভাল কৌশল।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. যদিও স্পোরগুলি মাইলের পর মাইল ভ্রমণ করতে পারে, বেশিরভাগই যেগুলি আপনার গাছকে সংক্রামিত করতে পারে সেগুলি কয়েকশ ফুটের মধ্যে রয়েছে।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. এটি করার সর্বোত্তম উপায় হ'ল পিত্তের নীচে প্রায় 4-6 ইঞ্চি শাখাগুলি ছাঁটাই করা।");
        	
        	
        }    else if(prediction.equals("Apple___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Apple");
        	responseEntity.getBody().setCropNameBn("আপেল");
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন !!!");
        	
        }   else if(prediction.equals("Blueberry___healthy")) {
        	responseEntity.getBody().setCropNameEn("Blueberry");
        	responseEntity.getBody().setCropNameBn("ব্লুবেরি");
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        }   
        
            else if(prediction.equals("Cherry_(including_sour)___Powdery_mildew")) {
            	
            	
        	responseEntity.getBody().setCropNameEn("Cherry");
        	responseEntity.getBody().setCropNameBn("চেরি");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Powdery Mildew");
        	responseEntity.getBody().setDiseaseNameBn("চূর্ণিত চিতা");
        	
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/>Podosphaera clandestina, একটি ছত্রাক যা সাধারণত অল্প বয়স্ক, প্রসারিত পাতাকে সংক্রমিত করে তবে কুঁড়ি, ফল এবং ফলের কান্ডেও পাওয়া যায়। এটি মরা পাতায়, বাগানের মেঝেতে বা গাছের খাঁজে ছোট, বৃত্তাকার, কালো দেহ (চাসমোথেসিয়া) হিসাবে অতিবাহিত হয়। উপনিবেশগুলি সাধারণত শাক পতনের আশেপাশে আরও (অযৌন) স্পোর তৈরি করে এবং রোগ চক্র চালিয়ে যায়।");
        	responseEntity.getBody().setCauseOfDiseaseEn("   <br/><br/>Podosphaera clandestina, a fungus that most commonly infects young, expanding leaves but can also be found on buds, fruit and fruit stems. It overwinters as small, round, black bodies (chasmothecia) on dead leaves, on the orchard floor, or in tree crotches. Colonies produce more (asexual) spores generally around shuck fall and continue the disease cycle.");
        	
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/>1. Remove and destroy sucker shoots.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Keep irrigation water off developing fruit and leaves by using irrigation that does not wet the leaves. Also, keep irrigation sets as short as possible.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Follow cultural practices that promote good air circulation, such as pruning, and moderate shoot growth through judicious nitrogen management.");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. চুষক অঙ্কুর অপসারণ এবং ধ্বংস.\r\n"
        			+ "\r\n"
        			+ "        <br/>২. সেচের পানি সেচের মাধ্যমে ফল ও পাতার বিকাশ বন্ধ রাখুন যাতে পাতা ভেজা না হয়। এছাড়াও, সেচ সেট যতটা সম্ভব ছোট রাখুন।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. সাংস্কৃতিক অনুশীলনগুলি অনুসরণ করুন যা ভাল বায়ু সঞ্চালনকে উত্সাহিত করে, যেমন ছাঁটাই, এবং ন্যায়সঙ্গত নাইট্রোজেন ব্যবস্থাপনার মাধ্যমে মাঝারি অঙ্কুর বৃদ্ধি।");
        	
        }    else if(prediction.equals("Cherry_(including_sour)___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Cherry");
        	responseEntity.getBody().setCropNameBn("চেরি");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        }     else if(prediction.equals("Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot")) {
        	
        	responseEntity.getBody().setCropNameEn("Corn");
        	responseEntity.getBody().setCropNameBn("ভুট্টা");
        	
        	responseEntity.getBody().setDiseaseNameEn("Grey Leaf Spot");
        	responseEntity.getBody().setDiseaseNameBn("ধূসর পাতার দাগ");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/>Gray leaf spot lesions on corn leaves hinder photosynthetic activity, reducing carbohydrates allocated towards grain fill. The extent to which gray leaf spot damages crop yields can be estimated based on the extent to which leaves are infected relative to grainfill. Damage can be more severe when developing lesions progress past the ear leaf around pollination time.	Because a decrease in functioning leaf area limits photosynthates dedicated towards grainfill, the plant might mobilize more carbohydrates from the stalk to fill kernels.");
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/>ভুট্টা পাতায় ধূসর পাতার দাগের ক্ষত সালোকসংশ্লেষী কার্যকলাপকে বাধাগ্রস্ত করে, শস্য ভরাটের জন্য বরাদ্দ কার্বোহাইড্রেট হ্রাস করে। ধূসর পাতার দাগ ফসলের ফলনের কতটা ক্ষতি করে তা অনুমান করা যেতে পারে শস্য পূরণের তুলনায় পাতাগুলি কতটা সংক্রমিত হয়েছে তার উপর ভিত্তি করে। ক্ষতি আরও গুরুতর হতে পারে যখন ক্ষতগুলি কানের পাতার পরে পরাগায়নের সময় অতিক্রম করে। যেহেতু পাতার ক্ষেত্রফলের কার্যকারিতা হ্রাস শস্য পূরণের জন্য নিবেদিত সালোকসংশ্লেষণকে সীমিত করে, গাছটি কার্নেল পূরণের জন্য ডাঁটা থেকে আরও কার্বোহাইড্রেট সংগ্রহ করতে পারে।");
        	
     
        	responseEntity.getBody().setPreventionOrCureEn("  <br/>1. In order to best prevent and manage corn grey leaf spot, the overall approach is to reduce the rate of disease growth and expansion.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. This is done by limiting the amount of secondary disease cycles and protecting leaf area from damage until after corn grain formation.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. High risk factors for grey leaf spot in corn: <br/>\r\n"
        			+ "                    a.	Susceptible hybrid\r\n"
        			+ "                    b.	Continuous corn\r\n"
        			+ "                    c.	Late planting date\r\n"
        			+ "                    d.	Minimum tillage systems\r\n"
        			+ "                    e.	Field history of severe disease\r\n"
        			+ "                    f.	Early disease activity (before tasseling)\r\n"
        			+ "                    g.	Irrigation\r\n"
        			+ "                    h.	Favorable weather forecast for disease.");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. ভুট্টার ধূসর পাতার দাগ প্রতিরোধ ও পরিচালনা করার জন্য, সামগ্রিক পদ্ধতি হল রোগের বৃদ্ধি এবং বিস্তারের হার কমানো।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. এটি গৌণ রোগ চক্রের পরিমাণ সীমিত করে এবং ভুট্টার দানা তৈরি হওয়ার আগে পর্যন্ত পাতার এলাকাকে ক্ষতির হাত থেকে রক্ষা করে করা হয়।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. ভুট্টায় ধূসর পাতার দাগের জন্য উচ্চ ঝুঁকির কারণগুলি: <br/>\r\n"
        			+ "                    ক সংবেদনশীল হাইব্রিড\r\n"
        			+ "                    খ. ক্রমাগত ভুট্টা\r\n"
        			+ "                    গ. দেরী রোপণের তারিখ\r\n"
        			+ "                    ঘ ন্যূনতম চাষ ব্যবস্থা\r\n"
        			+ "                    উং গুরুতর রোগের ক্ষেত্রের ইতিহাস\r\n"
        			+ "                    চ প্রারম্ভিক রোগ কার্যকলাপ (টাসেলিং আগে)\r\n"
        			+ "                    ছ সেচ\r\n"
        			+ "                    জ. রোগের জন্য অনুকূল আবহাওয়ার পূর্বাভাস।");
        	
        	
        }      else if(prediction.equals("Corn_(maize)___Common_rust_")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Corn");
        	responseEntity.getBody().setCropNameBn("ভুট্টা");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Common Rust");
        	responseEntity.getBody().setDiseaseNameBn("সাধারণ মরিচা");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/>সাধারণ ভুট্টা মরিচা, ছত্রাক Puccinia sorghi দ্বারা সৃষ্ট, মার্কিন যুক্তরাষ্ট্রে ভুট্টার দুটি প্রাথমিক মরিচা রোগের মধ্যে সবচেয়ে বেশি দেখা যায়, তবে এটি খুব কমই ওহাইও ক্ষেত্র (ডেন্ট) ভুট্টায় উল্লেখযোগ্য ফলন ক্ষতির কারণ হয় . মাঝে মাঝে ক্ষেতের ভুট্টা, বিশেষ করে রাজ্যের দক্ষিণ অর্ধে, যখন আবহাওয়া পরিস্থিতি মরিচা ছত্রাকের বিকাশ এবং বিস্তারের পক্ষে তখন মারাত্মকভাবে প্রভাবিত হয়");
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/>Common corn rust, caused by the fungus Puccinia sorghi, is the most frequently occurring of the two primary rust diseases of corn in the U.S., but it rarely causes significant yield losses in Ohio field (dent) corn. Occasionally field corn, particularly in the southern half of the state, does become severely affected when weather conditions favor the development and spread of rust fungus");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Although rust is frequently found on corn in Ohio, very rarely has there been a need for fungicide applications. This is due to the fact that there are highly resistant field corn hybrids available and most possess some degree of resistance.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. However, popcorn and sweet corn can be quite susceptible. In seasons where considerable rust is present on the lower leaves prior to silking and the weather is unseasonably cool and wet, an early fungicide application may be necessary for effective disease control. Numerous fungicides are available for rust control. \"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. যদিও ওহাইওতে ভুট্টায় প্রায়শই মরিচা পাওয়া যায়, তবে খুব কমই ছত্রাকনাশক প্রয়োগের প্রয়োজন হয়েছে। এটি এই কারণে যে এখানে অত্যন্ত প্রতিরোধী ক্ষেত ভুট্টা হাইব্রিড পাওয়া যায় এবং বেশিরভাগই কিছুটা প্রতিরোধের অধিকারী।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. তবে, পপকর্ন এবং মিষ্টি ভুট্টা বেশ সংবেদনশীল হতে পারে। যেসব ঋতুতে সিল্কিংয়ের আগে নীচের পাতায় যথেষ্ট মরিচা থাকে এবং আবহাওয়া অসময়ে শীতল এবং ভেজা থাকে, কার্যকরী রোগ নিয়ন্ত্রণের জন্য প্রাথমিক ছত্রাকনাশক প্রয়োগের প্রয়োজন হতে পারে। মরিচা নিয়ন্ত্রণের জন্য অসংখ্য ছত্রাকনাশক পাওয়া যায়।");
        	
        	
        }      else if(prediction.equals("Corn_(maize)___Northern_Leaf_Blight")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Corn");
        	responseEntity.getBody().setCropNameBn("ভুট্টা");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Northern Leaf Blight");
        	responseEntity.getBody().setDiseaseNameBn("নর্দার্ন লিফ ব্লাইট");
        	
        	
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/>Northern corn leaf blight (NCLB) is a foliar disease of corn (maize) caused by Exserohilum turcicum, the anamorph of the ascomycete Setosphaeria turcica. With its characteristic cigar-shaped lesions, this disease can cause significant yield loss in susceptible corn hybrids.");
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/>নর্দান কর্ন লিফ ব্লাইট (এনসিএলবি) হল ভুট্টার (ভুট্টার) একটি পাতার রোগ যা অ্যাসকোমাইসেট সেটোসফেরিয়া টারসিকার অ্যানামর্ফ এক্সসেরোহিলাম টারসিকাম দ্বারা সৃষ্ট হয়। এর বৈশিষ্ট্যযুক্ত সিগার-আকৃতির ক্ষতগুলির সাথে, এই রোগটি সংবেদনশীল ভুট্টা হাইব্রিডগুলিতে উল্লেখযোগ্য ফলন হ্রাস করতে পারে।");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Management of NCLB can be achieved primarily by using hybrids with resistance, but because resistance may not be complete or may fail, it is advantageous to utilize an integrated approach with different cropping practices and fungicides.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Scouting fields and monitoring local conditions is vital to control this disease.");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. এনসিএলবি ব্যবস্থাপনা প্রাথমিকভাবে প্রতিরোধ ক্ষমতা সহ হাইব্রিড ব্যবহার করে অর্জন করা যেতে পারে, কিন্তু যেহেতু প্রতিরোধ সম্পূর্ণ নাও হতে পারে বা ব্যর্থ হতে পারে, তাই বিভিন্ন শস্য চাষ পদ্ধতি এবং ছত্রাকনাশকের সাথে একটি সমন্বিত পদ্ধতি ব্যবহার করা সুবিধাজনক।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. এই রোগ নিয়ন্ত্রণের জন্য স্কাউটিং ক্ষেত্র এবং স্থানীয় অবস্থা পর্যবেক্ষণ করা অত্যাবশ্যক।");
        }    else if(prediction.equals("Grape___Black_rot")) {
        	
        	responseEntity.getBody().setCropNameEn("Grape");
        	responseEntity.getBody().setCropNameBn("আঙুর");
        	
        	responseEntity.getBody().setDiseaseNameEn("Black Rot");
        	responseEntity.getBody().setDiseaseNameBn("কালো পচা");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. কালো পচা ছত্রাক শীতকালে বেত, টেন্ড্রিল এবং পাতায় আঙ্গুরের লতা এবং জমিতে থাকে। মাটিতে মমি করা বেরি বা যেগুলি এখনও লতাগুলিতে আঁকড়ে আছে তা পরবর্তী বসন্তে সংক্রমণের প্রধান উত্স হয়ে ওঠে।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. বৃষ্টির সময়, আণুবীক্ষণিক স্পোর (অ্যাসকোস্পোর) অসংখ্য কালো ফলদায়ক দেহ (পেরিথেসিয়া) থেকে ছিটকে যায় এবং বায়ু প্রবাহের মাধ্যমে কচি, প্রসারিত পাতায় নিয়ে যায়। আর্দ্রতার উপস্থিতিতে, এই স্পোরগুলি 36 থেকে 48 ঘন্টার মধ্যে অঙ্কুরিত হয় এবং অবশেষে পাতা এবং ফলের কান্ডে প্রবেশ করে।\r\n"
        			+ "\r\n"
        			+ "        <br/> 3. 8 থেকে 25 দিন পরে সংক্রমণ দৃশ্যমান হয়। যখন আবহাওয়া ভেজা থাকে, তখন পুরো বসন্ত এবং গ্রীষ্মে স্পোর নির্গত হতে পারে যা ক্রমাগত সংক্রমণ প্রদান করে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/> 1. The black rot fungus overwinters in canes, tendrils, and leaves on the grape vine and on the ground. Mummified berries on the ground or those that are still clinging to the vines become the major infection source the following spring.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. During rain, microscopic spores (ascospores) are shot out of numerous, black fruiting bodies (perithecia) and are carried by air currents to young, expanding leaves. In the presence of moisture, these spores germinate in 36 to 48 hours and eventually penetrate the leaves and fruit stems. \r\n"
        			+ "\r\n"
        			+ "        <br/> 3. The infection becomes visible after 8 to 25 days. When the weather is wet, spores can be released the entire spring and summer providing continuous infection.\r\n"
        			+ "\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Space vines properly and choose a planting site where the vines will be exposed to full sun and good air circulation. Keep the vines off the ground and insure they are properly tied, limiting the amount of time the vines remain wet thus reducing infection.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Keep the fruit planting and surrounding areas free of weeds and tall grass. This practice will promote lower relative humidity and rapid drying of vines and thereby limit fungal infection.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Use protective fungicide sprays. Pesticides registered to protect the developing new growth include copper, captan, ferbam, mancozeb, maneb, triadimefon, and ziram. Important spraying times are as new shoots are 2 to 4 inches long, and again when they are 10 to 15 inches long, just before bloom, just after bloom, and when the fruit has set.\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. স্পেস দ্রাক্ষালতা সঠিকভাবে এবং একটি রোপণ স্থান নির্বাচন করুন যেখানে দ্রাক্ষালতা পূর্ণ সূর্য এবং ভাল বায়ু সঞ্চালন উন্মুক্ত করা হবে. দ্রাক্ষালতাগুলিকে মাটি থেকে দূরে রাখুন এবং নিশ্চিত করুন যে তারা সঠিকভাবে বাঁধা আছে, দ্রাক্ষালতাগুলি ভেজা থাকার পরিমাণ সীমিত করে যাতে সংক্রমণ হ্রাস পায়।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. ফল রোপণ ও আশপাশের এলাকা আগাছা ও লম্বা ঘাসমুক্ত রাখুন। এই অনুশীলনটি কম আপেক্ষিক আর্দ্রতা এবং লতাগুলির দ্রুত শুকিয়ে যাওয়ার প্রচার করবে এবং এর ফলে ছত্রাকের সংক্রমণ সীমিত হবে।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. প্রতিরক্ষামূলক ছত্রাকনাশক স্প্রে ব্যবহার করুন। উন্নয়নশীল নতুন বৃদ্ধি রক্ষার জন্য নিবন্ধিত কীটনাশকগুলির মধ্যে রয়েছে তামা, ক্যাপ্টান, ফারবাম, ম্যানকোজেব, মানেব, ট্রায়াডিমেফন এবং জিরাম। স্প্রে করার গুরুত্বপূর্ণ সময় হল নতুন অঙ্কুর 2 থেকে 4 ইঞ্চি লম্বা, এবং আবার যখন সেগুলি 10 থেকে 15 ইঞ্চি লম্বা হয়, ফুল ফোটার ঠিক আগে, ফুল ফোটার ঠিক পরে এবং ফল সেট হয়ে গেলে।");
        	
        }   else if(prediction.equals("Corn_(maize)___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Corn");
        	responseEntity.getBody().setCropNameBn("ভুট্টা");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        }    else if(prediction.equals("Grape___Esca_(Black_Measles)")) {
        	
        	

        	responseEntity.getBody().setCropNameEn("Grape");
        	responseEntity.getBody().setCropNameBn("আঙুর");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Black Measles");
        	responseEntity.getBody().setDiseaseNameBn("কালো হাম");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. কালো হাম একটি জটিল ছত্রাক দ্বারা সৃষ্ট হয় যার মধ্যে বিভিন্ন প্রজাতির P. aleophilum (বর্তমানে এটির যৌন পর্যায়, Togninia minima নামে পরিচিত), এবং Phaeomoniella chlamydospora দ্বারা বিভিন্ন প্রজাতির ছত্রাক রয়েছে। .\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. অতিরিক্ত শীতকালীন কাঠামো যা স্পোর তৈরি করে (পেরিথেসিয়া বা পাইকনিডিয়া, প্যাথোজেনের উপর নির্ভর করে) দ্রাক্ষালতার রোগাক্রান্ত কাঠের অংশগুলিতে এম্বেড করা হয়। অতিরিক্ত শীতকালীন কাঠামো যা স্পোর তৈরি করে (পেরিথেসিয়া বা পাইকনিডিয়া, প্যাথোজেনের উপর নির্ভর করে) দ্রাক্ষালতার রোগাক্রান্ত কাঠের অংশে এম্বেড করা হয়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 3. শরত থেকে বসন্তের বৃষ্টিপাতের সময়, স্পোর নির্গত হয় এবং সুপ্ত ছাঁটাই দ্বারা তৈরি ক্ষতগুলি সংক্রমণের স্থানগুলি সরবরাহ করে।\r\n"
        			+ "\r\n"
        			+ "        <br/> 4. সময়ের সাথে সংবেদনশীলতা হ্রাস সহ ক্ষতগুলি ছাঁটাই করার পরে কয়েক সপ্তাহ সংক্রমণের জন্য সংবেদনশীল থাকতে পারে।");
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/> 1. Black Measles is caused by a complex of fungi that includes several species of Phaeoacremonium, primarily by P. aleophilum (currently known by the name of its sexual stage, Togninia minima), and by Phaeomoniella chlamydospora.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. The overwintering structures that produce spores (perithecia or pycnidia, depending on the pathogen) are embedded in diseased woody parts of vines. The overwintering structures that produce spores (perithecia or pycnidia, depending on the pathogen) are embedded in diseased woody parts of vines.\r\n"
        			+ "\r\n"
        			+ "        <br/> 3. During fall to spring rainfall, spores are released and wounds made by dormant pruning provide infection sites.\r\n"
        			+ "\r\n"
        			+ "        <br/> 4. Wounds may remain susceptible to infection for several weeks after pruning with susceptibility declining over time.\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/>1. Post-infection practices (sanitation and vine surgery) for use in diseased, mature vineyards are not as effective and are far more costly than adopting preventative practices (delayed pruning, double pruning, and applications of pruning-wound protectants) in young vineyards. \r\n"
        			+ "\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Sanitation and vine surgery may help maintain yields. In spring, look for dead spurs or for stunted shoots. Later in summer, when there is a reduced chance of rainfall, practice good sanitation by cutting off these cankered portions of the vine beyond the canker, to where wood appears healthy. Then remove diseased, woody debris from the vineyard and destroy it.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. The fungicides labeled as pruning-wound protectants, consider using alternative materials, such as a wound sealant with 5 percent boric acid in acrylic paint (Tech-Gro B-Lock), which is effective against Eutypa dieback and Esca, or an essential oil (Safecoat VitiSeal).\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. রোগাক্রান্ত, পরিপক্ক দ্রাক্ষাক্ষেত্রে ব্যবহারের জন্য সংক্রমণ-পরবর্তী অনুশীলনগুলি (স্যানিটেশন এবং লতা সার্জারি) ততটা কার্যকর নয় এবং প্রতিরোধমূলক পদ্ধতিগুলি গ্রহণ করার চেয়ে অনেক বেশি ব্যয়বহুল (বিলম্বিত ছাঁটাই, দ্বিগুণ ছাঁটাই, এবং ছাঁটাই-ক্ষত প্রয়োগ) রক্ষাকারী) তরুণ দ্রাক্ষাক্ষেত্রে। \r\n"
        	        + "\r\n"
        	        + "\r\n"
        	        + " <br/>2. স্যানিটেশন এবং লতা সার্জারি ফলন বজায় রাখতে সাহায্য করতে পারে। বসন্তে, মৃত স্পার্স বা স্তব্ধ অঙ্কুরগুলির জন্য দেখুন। গ্রীষ্মের পরে, যখন বৃষ্টিপাতের সম্ভাবনা কম থাকে, তখন এই ক্যাঙ্কার্ডগুলি কেটে ভাল স্যানিটেশন অনুশীলন করুন। ক্যাঙ্কারের বাইরে দ্রাক্ষালতার অংশ, যেখানে কাঠ স্বাস্থ্যকর দেখায়। তারপর দ্রাক্ষাক্ষেত্র থেকে রোগাক্রান্ত, কাঠের ধ্বংসাবশেষ সরিয়ে ফেলুন এবং ধ্বংস করুন।\r\n"
        	        + " \r\n"
        	        + " <br/>3. ছাঁটাই-ক্ষত রক্ষাকারী হিসাবে লেবেলযুক্ত ছত্রাকনাশক, বিকল্প উপকরণগুলি ব্যবহার করার কথা বিবেচনা করুন, যেমন অ্যাক্রিলিক পেইন্টে 5 শতাংশ বোরিক অ্যাসিডযুক্ত ক্ষত সিলান্ট (টেক-গ্রো বি-লক), যা ইউটাইপা ডাইব্যাকের বিরুদ্ধে কার্যকর। এবং Esca, বা একটি অপরিহার্য তেল (সেফকোট ভিটিসিল)।\"\"\",\r\n"
        	        + "");
        	
        	
        	
        }    else if(prediction.equals("Grape___Leaf_blight_(Isariopsis_Leaf_Spot)")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Grape");
        	responseEntity.getBody().setCropNameBn("আঙ্গুর");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Leaf Blight");
        	responseEntity.getBody().setDiseaseNameBn("লিফ ব্লাইট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. আপেল স্ক্যাব প্রধানত পতিত পাতায় এবং মাটিতে শীতকালে পড়ে। রোগের বিকাশ সাধারনত বসন্ত এবং গ্রীষ্মের শুরুতে ভেজা, শীতল আবহাওয়া দ্বারা অনুকূল হয়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. ছত্রাকের বীজ বাতাস, বৃষ্টি বা ছিটকে পড়া জলের মাধ্যমে মাটি থেকে ফুল, পাতা বা ফলের মধ্যে বহন করে। স্যাঁতসেঁতে বা বৃষ্টির সময়, নতুন খোলা আপেল পাতা সংক্রমণের জন্য অত্যন্ত সংবেদনশীল। পাতা যত বেশি সময় ভেজা থাকবে, সংক্রমণ তত বেশি হবে। আপেল স্ক্যাব 55-75 ডিগ্রি ফারেনহাইটের মধ্যে দ্রুত ছড়িয়ে পড়ে।\r\n"
        			+ "        <br/><br/> কিভাবে রোগ প্রতিরোধ/নিরাময় করা যায় <br/>");
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/> 1. Apple scab overwinters primarily in fallen leaves and in the soil. Disease development is favored by wet, cool weather that generally occurs in spring and early summer.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. Fungal spores are carried by wind, rain or splashing water from the ground to flowers, leaves or fruit. During damp or rainy periods, newly opening apple leaves are extremely susceptible to infection. The longer the leaves remain wet, the more severe the infection will be. Apple scab spreads rapidly between 55-75 degrees Fahrenheit.\r\n"
        			+ "        <br/><br/> How to prevent/cure the disease <br/>");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Choose resistant varieties when possible.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Rake under trees and destroy infected leaves to reduce the number of fungal spores available to start the disease cycle over again next spring\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Water in the evening or early morning hours (avoid overhead irrigation) to give the leaves time to dry out before infection can occur.\r\n"
        			+ "        <br/>4. Spread a 3- to 6-inch layer of compost under trees, keeping it away from the trunk, to cover soil and prevent splash dispersal of the fungal spores.\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. সম্ভব হলে প্রতিরোধী জাত বেছে নিন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. পরবর্তী বসন্তে আবার রোগ চক্র শুরু করার জন্য উপলব্ধ ছত্রাকের বীজের সংখ্যা কমাতে গাছের নিচে রেক করুন এবং সংক্রামিত পাতা ধ্বংস করুন\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. সন্ধ্যায় বা ভোরে পানি দিন (ওভারহেড সেচ এড়িয়ে চলুন) যাতে সংক্রমণ হওয়ার আগে পাতা শুকিয়ে যায়।\r\n"
        			+ "        <br/>৪. গাছের নিচে 3 থেকে 6-ইঞ্চি কম্পোস্টের স্তর ছড়িয়ে দিন, এটি কাণ্ড থেকে দূরে রেখে, মাটি ঢেকে রাখুন এবং ছত্রাকের স্পোর ছড়িয়ে পড়া রোধ করুন।");
      
        } else if(prediction.equals("Grape___healthy")) {
        	
        	

        	responseEntity.getBody().setCropNameEn("Grape");
        	responseEntity.getBody().setCropNameBn("আঙ্গুর");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        } else if(prediction.equals("Corn_(maize)___healthy")) {
        	
        	
        	
        	responseEntity.getBody().setCropNameEn("Corn");
        	responseEntity.getBody().setCropNameBn("ভুট্টা");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        }  else if(prediction.equals("Orange___Haunglongbing_(Citrus_greening)")) {
        	
        	responseEntity.getBody().setCropNameEn("Orange");
        	responseEntity.getBody().setCropNameBn("কমলা");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Citrus Greening");
        	responseEntity.getBody().setDiseaseNameBn("সাইট্রাস সবুজায়ন");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> হুয়াংলংবিং (এইচএলবি) বা সাইট্রাস গ্রিনিং হল সবচেয়ে মারাত্মক সাইট্রাস রোগ, যা বর্তমানে বিশ্বব্যাপী সাইট্রাস শিল্পকে ধ্বংস করছে। অনুমান কারণ ব্যাকটেরিয়া এজেন্ট Candidatus Liberibacter spp. গাছের স্বাস্থ্যের পাশাপাশি ফলের বিকাশ, পাকা এবং সাইট্রাস ফল এবং রসের গুণমানকে প্রভাবিত করে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/>  Huanglongbing (HLB) or citrus greening is the most severe citrus disease, currently devastating the citrus industry worldwide. The presumed causal bacterial agent Candidatus Liberibacter spp. affects tree health as well as fruit development, ripening and quality of citrus fruits and juice.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/>1. In regions where disease incidence is low, the most common practices are avoiding the spread of infection by removal of symptomatic trees, protecting grove edges through intensive monitoring, use of pesticides, and biological control of the vector ACP.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. According to Singerman and Useche (2016), CHMAs coordinate insecticide application to control the ACP spreading across area-wide neighboring commercial citrus groves as part of a plan to address the HLB disease.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. In addition to foliar nutritional sprays, plant growth regulators were tested, unsuccessfully, to reduce HLB-associated fruit drop (Albrigo and Stover, 2015).\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. যেসব অঞ্চলে রোগের প্রকোপ কম, সেখানে সবচেয়ে সাধারণ অভ্যাস হল লক্ষণযুক্ত গাছ অপসারণ, নিবিড় পর্যবেক্ষণের মাধ্যমে গ্রোভের প্রান্ত রক্ষা, কীটনাশক ব্যবহার এবং ভেক্টর ACP-এর জৈবিক নিয়ন্ত্রণের মাধ্যমে সংক্রমণের বিস্তার এড়ানো।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. Singerman and Useche (2016) এর মতে, CHMAs HLB রোগ মোকাবেলার পরিকল্পনার অংশ হিসাবে এলাকা-ব্যাপী প্রতিবেশী বাণিজ্যিক সাইট্রাস গ্রোভ জুড়ে ছড়িয়ে পড়া ACP নিয়ন্ত্রণ করতে কীটনাশক প্রয়োগের সমন্বয় করে।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. ফলিয়ার নিউট্রিশনাল স্প্রে ছাড়াও, HLB-এর সাথে যুক্ত ফলের ড্রপ (আলব্রিগো এবং স্টোভার, 2015) কমানোর জন্য গাছের বৃদ্ধির নিয়ন্ত্রকদের পরীক্ষা করা হয়েছিল।");
        	
        	
        	
        }   else if(prediction.equals("Peach___Bacterial_spot")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Peach");
        	responseEntity.getBody().setCropNameBn("পীচ");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Bacterial Spot");
        	responseEntity.getBody().setDiseaseNameBn("ব্যাকটেরিয়াল স্পট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. জ্যান্থোমোনাসের চারটি প্রজাতির (এক্স. ইউভেসিকেটোরিয়া, এক্স. গার্ডনেরি, এক্স. পারফোরানস এবং এক্স. ভেসিকেটোরিয়া) রোগটি হয়ে থাকে। উত্তর ক্যারোলিনায়, X. perforans হল টমেটোতে ব্যাকটেরিয়ার দাগের সাথে যুক্ত প্রধান প্রজাতি এবং X. euvesicatoria হল মরিচের রোগের সাথে যুক্ত প্রধান প্রজাতি।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. চারটি ব্যাকটেরিয়াই কঠোরভাবে বায়বীয়, গ্রাম-নেগেটিভ রড যার একটি লম্বা চাবুকের মতো ফ্ল্যাজেলাম (লেজ) যা তাদের পানিতে চলাচল করতে দেয়, যা তাদের ভেজা উদ্ভিদের টিস্যুতে আক্রমণ করতে দেয় এবং সংক্রমণ ঘটায়।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/> 1. The disease is caused by four species of Xanthomonas (X. euvesicatoria, X. gardneri, X. perforans, and X. vesicatoria). In North Carolina, X. perforans is the predominant species associated with bacterial spot on tomato and X. euvesicatoria is the predominant species associated with the disease on pepper.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. All four bacteria are strictly aerobic, gram-negative rods with a long whip-like flagellum (tail) that allows them to move in water, which allows them to invade wet plant tissue and cause infection.\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. The most effective management strategy is the use of pathogen-free certified seeds and disease-free transplants to prevent the introduction of the pathogen into greenhouses and field production areas. Inspect plants very carefully and reject infected transplants- including your own!\r\n"
        			+ "\r\n"
        			+ "        <br/>2. In transplant production greenhouses, minimize overwatering and handling of seedlings when they are wet.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Trays, benches, tools, and greenhouse structures should be washed and sanitized between seedlings crops.\r\n"
        			+ "        <br/>4. Do not spray, tie, harvest, or handle wet plants as that can spread the disease.");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. সবচেয়ে কার্যকরী ব্যবস্থাপনার কৌশল হল প্যাথোজেন-মুক্ত প্রত্যয়িত বীজ এবং রোগ-মুক্ত প্রতিস্থাপনের ব্যবহার যাতে গ্রীনহাউস এবং মাঠ উৎপাদন এলাকায় রোগজীবাণুর প্রবেশ রোধ করা যায়। খুব সাবধানে উদ্ভিদ পরিদর্শন করুন এবং সংক্রামিত প্রতিস্থাপন প্রত্যাখ্যান করুন- আপনার নিজের সহ!\r\n"
        			+ "\r\n"
        			+ "        <br/>২. ট্রান্সপ্লান্ট উৎপাদন গ্রীনহাউসে, চারা ভেজা অবস্থায় অতিরিক্ত জল দেওয়া এবং পরিচালনা করা কমিয়ে দিন।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. ট্রে, বেঞ্চ, টুলস এবং গ্রিনহাউসের কাঠামো ধুয়ে এবং স্যানিটাইজ করা উচিত চারা ফসলের মধ্যে।\r\n"
        			+ "        <br/>৪. স্প্রে করবেন না, বাঁধবেন না, ফসল কাটাবেন না বা ভেজা গাছগুলি পরিচালনা করবেন না কারণ এটি রোগ ছড়াতে পারে।");
        	
        }  else if(prediction.equals("Pepper,_bell___Bacterial_spot")) {
        	
        	

        	responseEntity.getBody().setCropNameEn("Pepper");
        	responseEntity.getBody().setCropNameBn("মরিচ");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Bacterial Spot");
        	responseEntity.getBody().setDiseaseNameBn("ব্যাকটেরিয়াল স্পট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. জ্যান্থোমোনাস গোত্রের বিভিন্ন প্রজাতির গ্রাম-নেগেটিভ ব্যাকটেরিয়ার কারণে ব্যাকটেরিয়াজনিত দাগ হয়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. সংস্কৃতিতে, এই ব্যাকটেরিয়াগুলি হলুদ, মিউকয়েড কলোনি তৈরি করে। পাতার ক্ষত দিয়ে ক্রস-সেকশনাল কাট করে, টিস্যুকে এক ফোঁটা জলে রেখে, নমুনার উপরে কভার-স্লিপ রেখে এবং মাইক্রোস্কোপ দিয়ে পরীক্ষা করে ক্ষত থেকে ব্যাকটেরিয়ার \"ভর\" বের হতে দেখা যায় ( ~200X)।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/> 1. Bacterial spot is caused by several species of gram-negative bacteria in the genus Xanthomonas.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. In culture, these bacteria produce yellow, mucoid colonies. A \"mass\" of bacteria can be observed oozing from a lesion by making a cross-sectional cut through a leaf lesion, placing the tissue in a droplet of water, placing a cover-slip over the sample, and examining it with a microscope (~200X)..\r\n"
        			+ "        ");
        	
        	responseEntity.getBody().setPreventionOrCureEn("  <br/>1. The primary management strategy of bacterial spot begins with use of certified pathogen-free seed and disease-free transplants.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. The bacteria do not survive well once host material has decayed, so crop rotation is recommended. Once the bacteria are introduced into a field or greenhouse, the disease is very difficult to control.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Pepper plants are routinely sprayed with copper-containing bactericides to maintain a \"protective\" cover on the foliage and fruit.\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. ব্যাকটেরিয়া দাগের প্রাথমিক ব্যবস্থাপনার কৌশলটি প্রত্যয়িত প্যাথোজেন-মুক্ত বীজ এবং রোগ-মুক্ত প্রতিস্থাপনের মাধ্যমে শুরু হয়।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. হোস্ট উপাদান ক্ষয় হয়ে গেলে ব্যাকটেরিয়া ভালভাবে বেঁচে থাকে না, তাই ফসল ঘোরানোর পরামর্শ দেওয়া হয়। ব্যাকটেরিয়া একবার মাঠ বা গ্রিনহাউসে প্রবেশ করলে রোগ নিয়ন্ত্রণ করা খুবই কঠিন।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. মরিচ গাছের পাতা এবং ফলের উপর একটি \"প্রতিরক্ষামূলক\" আবরণ বজায় রাখার জন্য নিয়মিতভাবে তামাযুক্ত ব্যাকটেরিয়ানাশক স্প্রে করা হয়।");
        	
        	
        	
        }  else if(prediction.equals("Peach___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Peach");
        	responseEntity.getBody().setCropNameBn("পীচ");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        }  else if(prediction.equals("Pepper,_bell___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Pepper");
        	responseEntity.getBody().setCropNameBn("মরিচ");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        } else if(prediction.equals("Potato___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Potato");
        	responseEntity.getBody().setCropNameBn("আলু");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        } else if(prediction.equals("Raspberry___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Raspberry");
        	responseEntity.getBody().setCropNameBn("রাস্পবেরি");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        }else if(prediction.equals("Soybean___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Soyabean");
        	responseEntity.getBody().setCropNameBn("সয়াবিন");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        }else if(prediction.equals("Strawberry___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Strawberry");
        	responseEntity.getBody().setCropNameBn("স্ট্রবেরি");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        } else if(prediction.equals("Tomato___healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        } else if(prediction.equals("Potato___Early_blight")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Potato");
        	responseEntity.getBody().setCropNameBn("আলু");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("EarlyBlight");
        	responseEntity.getBody().setDiseaseNameBn("প্রারম্ভিক ব্লাইট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. আর্লি ব্লাইট (EB) আলটারনারিয়া সোলানি নামক ছত্রাক দ্বারা সৃষ্ট আলুর একটি রোগ। যেখানেই আলু হয় সেখানেই পাওয়া যায়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. এই রোগটি প্রাথমিকভাবে পাতা এবং কান্ডকে প্রভাবিত করে, কিন্তু অনুকূল আবহাওয়ার অধীনে, এবং যদি অনিয়ন্ত্রিত রেখে দেওয়া হয়, তাহলে যথেষ্ট পরিমাণে ক্ষয় হতে পারে এবং কন্দ সংক্রমণের সম্ভাবনা বাড়িয়ে তুলতে পারে। অকাল পতনের ফলে ফলন উল্লেখযোগ্যভাবে হ্রাস পেতে পারে।\r\n"
        			+ "\r\n"
        			+ "        <br/> 3. প্রাথমিক সংক্রমণ ভবিষ্যদ্বাণী করা কঠিন কারণ ইবি নির্দিষ্ট আবহাওয়ার উপর দেরী ব্লাইটের চেয়ে কম নির্ভরশীল।");
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/> 1. Early blight (EB) is a disease of potato caused by the fungus Alternaria solani. It is found wherever potatoes are grown. \r\n"
        			+ "\r\n"
        			+ "        <br/> 2. The disease primarily affects leaves and stems, but under favorable weather conditions, and if left uncontrolled, can result in considerable defoliation and enhance the chance for tuber infection. Premature defoliation may lead to considerable reduction in yield.\r\n"
        			+ "\r\n"
        			+ "        <br/> 3. Primary infection is difficult to predict since EB is less dependent upon specific weather conditions than late blight.");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Plant only diseasefree, certified seed. \r\n"
        			+ "\r\n"
        			+ "        <br/>2. Follow a complete and regular foliar fungicide spray program.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Practice good killing techniques to lessen tuber infections.\r\n"
        			+ "        <br/>4. Allow tubers to mature before digging, dig when vines are dry, not wet, and avoid excessive wounding of potatoes during harvesting and handling.\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. শুধুমাত্র রোগমুক্ত, প্রত্যয়িত বীজ রোপণ করুন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. একটি সম্পূর্ণ এবং নিয়মিত ফলিয়ার ছত্রাকনাশক স্প্রে প্রোগ্রাম অনুসরণ করুন।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. কন্দ সংক্রমণ কমাতে ভাল হত্যা কৌশল অনুশীলন করুন।\r\n"
        			+ "        <br/>৪. খনন করার আগে কন্দগুলিকে পরিপক্ক হতে দিন, লতাগুলি শুকিয়ে গেলে খনন করুন, ভেজা নয় এবং আলু কাটা ও পরিচালনার সময় অতিরিক্ত ক্ষত এড়ান।");
        	
        	
        } else if(prediction.equals("Potato___Late_blight")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Potato");
        	responseEntity.getBody().setCropNameBn("আলু");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Late Blight");
        	responseEntity.getBody().setDiseaseNameBn("দেরী ব্লাইট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. দেরী ব্লাইট oomycete Phytophthora infestans দ্বারা সৃষ্ট হয়। Oomycetes হল ছত্রাকের মতো জীব যাকে জলের ছাঁচও বলা হয়, কিন্তু এগুলি সত্যিকারের ছত্রাক নয়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. P. infestans এর বিভিন্ন স্ট্রেন আছে। এগুলিকে ক্লোনাল বংশ বলা হয় এবং একটি সংখ্যা কোড দ্বারা মনোনীত করা হয় (যেমন US-23)। অনেক ক্লোনাল বংশ টমেটো এবং আলু উভয়কেই প্রভাবিত করে, তবে কিছু বংশ একটি হোস্ট বা অন্যের জন্য নির্দিষ্ট।\r\n"
        			+ "        <br/> 3. হোস্ট পরিসর সাধারণত আলু এবং টমেটোর মধ্যে সীমাবদ্ধ, তবে লোমশ নাইটশেড (সোলানাম ফিজালিফোলিয়াম) একটি ঘনিষ্ঠভাবে সম্পর্কিত আগাছা যা সহজেই সংক্রামিত হতে পারে এবং রোগ বিস্তারে অবদান রাখতে পারে। আদর্শ অবস্থার অধীনে, যেমন গ্রিনহাউস, পেটুনিয়াও সংক্রামিত হতে পারে।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/> 1. Late blight is caused by the oomycete Phytophthora infestans. Oomycetes are fungus-like organisms also called water molds, but they are not true fungi.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. There are many different strains of P. infestans. These are called clonal lineages and designated by a number code (i.e. US-23). Many clonal lineages affect both tomato and potato, but some lineages are specific to one host or the other.\r\n"
        			+ "        <br/> 3. The host range is typically limited to potato and tomato, but hairy nightshade (Solanum physalifolium) is a closely related weed that can readily become infected and may contribute to disease spread. Under ideal conditions, such as a greenhouse, petunia also may become infected.\r\n"
        			+ "        ");
        	
        	responseEntity.getBody().setPreventionOrCureEn("  <br/>1. Seed infection is unlikely on commercially prepared tomato seed or on saved seed that has been thoroughly dried.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Inspect tomato transplants for late blight symptoms prior to purchase and/or planting, as tomato transplants shipped from southern regions may be infected\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. If infection is found in only a few plants within a field, infected plants should be removed, disced-under, killed with herbicide or flame-killed to avoid spreading through the entire field.\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. বাণিজ্যিকভাবে প্রস্তুত টমেটো বীজ বা ভালোভাবে শুকানো বীজে বীজ সংক্রমণের সম্ভাবনা নেই।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. ক্রয় এবং/অথবা রোপণের আগে দেরী ব্লাইটের লক্ষণগুলির জন্য টমেটো ট্রান্সপ্ল্যান্ট পরিদর্শন করুন, কারণ দক্ষিণ অঞ্চল থেকে পাঠানো টমেটো ট্রান্সপ্ল্যান্ট সংক্রামিত হতে পারে\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. যদি একটি ক্ষেতের মধ্যে মাত্র কয়েকটি গাছে সংক্রমণ পাওয়া যায়, তবে সংক্রামিত গাছগুলিকে সরিয়ে ফেলতে হবে, নীচে কেটে ফেলতে হবে, ভেষজনাশক দিয়ে মেরে ফেলতে হবে বা পুরো ক্ষেতে ছড়িয়ে পড়া এড়াতে শিখা মেরে ফেলতে হবে।");
        	
        	
        }  else if(prediction.equals("Squash___Powdery_mildew")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Squash");
        	responseEntity.getBody().setCropNameBn("স্কোয়াশ");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Powdery mildew");
        	responseEntity.getBody().setDiseaseNameBn("চূর্ণিত চিতা");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. পাউডারি মিলডিউ সংক্রমণ 68-81° ফারেনহাইট তাপমাত্রার সাথে আর্দ্র অবস্থার পক্ষে\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. উষ্ণ, শুষ্ক অবস্থায় নতুন স্পোর তৈরি হয় এবং সহজেই রোগ ছড়ায়।\r\n"
        			+ "        <br/> 3. মিনেসোটাতে গ্রীষ্মের মাঝামাঝি থেকে শেষের দিকে পাউডারি মিলডিউ-এর লক্ষণ প্রথম দেখা যায়। পুরানো পাতাগুলি বেশি সংবেদনশীল এবং পাউডারি মিলডিউ প্রথমে তাদের সংক্রমিত করবে।\r\n"
        			+ "        <br/> 4. বাতাস পাতার দাগে উত্পাদিত স্পোরগুলিকে অন্য পাতাকে সংক্রমিত করার জন্য উড়িয়ে দেয়।\r\n"
        			+ "        <br/> 5. অনুকূল পরিস্থিতিতে, পাউডারি মিল্ডিউ খুব দ্রুত ছড়িয়ে পড়তে পারে, প্রায়ই সমস্ত পাতা ঢেকে দেয়।");
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/> 1. Powdery mildew infections favor humid conditions with temperatures around 68-81° F\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. In warm, dry conditions, new spores form and easily spread the disease.\r\n"
        			+ "        <br/> 3. Symptoms of powdery mildew first appear mid to late summer in Minnesota.  The older leaves are more susceptible and powdery mildew will infect them first.\r\n"
        			+ "        <br/> 4. Wind blows spores produced in leaf spots to infect other leaves.\r\n"
        			+ "        <br/> 5. Under favorable conditions, powdery mildew can spread very rapidly, often covering all of the leaves.\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Apply fertilizer based on soil test results. Avoid over-applying nitrogen.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Provide good air movement around plants through proper spacing, staking of plants and weed control.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Once a week, examine five mature leaves for powdery mildew infection. In large plantings, repeat at 10 different locations in the field.\r\n"
        			+ "        <br/>4. If susceptible varieties are growing in an area where powdery mildew has resulted in yield loss in the past, fungicide may be necessary.\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. মাটি পরীক্ষার ফলাফলের ভিত্তিতে সার প্রয়োগ করুন। অতিরিক্ত নাইট্রোজেন প্রয়োগ করা এড়িয়ে চলুন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. সঠিক ব্যবধান, গাছপালা আটকানো এবং আগাছা নিয়ন্ত্রণের মাধ্যমে গাছের চারপাশে ভাল বায়ু চলাচলের ব্যবস্থা করুন।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. সপ্তাহে একবার, পাউডারি মিলডিউ সংক্রমণের জন্য পাঁচটি পরিপক্ক পাতা পরীক্ষা করুন। বড় রোপণে, ক্ষেত্রের 10টি ভিন্ন স্থানে পুনরাবৃত্তি করুন।\r\n"
        			+ "        <br/>৪. যদি সংবেদনশীল জাতগুলি এমন একটি এলাকায় বৃদ্ধি পায় যেখানে অতীতে পাউডারি মিলডিউর ফলে ফলন হ্রাস পায়, তাহলে ছত্রাকনাশকের প্রয়োজন হতে পারে।");
        	
        	
        } else if(prediction.equals("Strawberry___Leaf_scorch")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Strawberry");
        	responseEntity.getBody().setCropNameBn("স্ট্রবেরি");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Leaf Scorch");
        	responseEntity.getBody().setDiseaseNameBn("পাতা ঝলসানো");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. ঝলসে যাওয়া স্ট্রবেরি পাতা একটি ছত্রাকের সংক্রমণের কারণে হয় যা স্ট্রবেরি গাছের পাতাকে প্রভাবিত করে। দায়ী ছত্রাককে ডিপ্লোকারপন এরলিয়ানা বলা হয়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. পাতা ঝলসানো স্ট্রবেরি প্রথমে পাতার উপরের দিকে ছোট বেগুনি দাগের বিকাশের সাথে সমস্যার লক্ষণ দেখাতে পারে।");
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/> 1. Scorched strawberry leaves are caused by a fungal infection which affects the foliage of strawberry plantings. The fungus responsible is called Diplocarpon earliana.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. Strawberries with leaf scorch may first show signs of issue with the development of small purplish blemishes that occur on the topside of leaves.\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Since this fungal pathogen over winters on the fallen leaves of infect plants, proper garden sanitation is key.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. This includes the removal of infected garden debris from the strawberry patch, as well as the frequent establishment of new strawberry transplants.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. The avoidance of waterlogged soil and frequent garden cleanup will help to reduce the likelihood of spread of this fungus.\"\"\",\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. যেহেতু এই ছত্রাকজনিত রোগজীবাণু শীতকালে গাছের পতিত পাতায় সংক্রমিত হয়, তাই সঠিক বাগান স্যানিটেশন চাবিকাঠি।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. এর মধ্যে স্ট্রবেরি প্যাচ থেকে সংক্রামিত বাগানের ধ্বংসাবশেষ অপসারণের পাশাপাশি নতুন স্ট্রবেরি প্রতিস্থাপনের ঘন ঘন স্থাপন অন্তর্ভুক্ত রয়েছে।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. জলাবদ্ধ মাটি এড়ানো এবং ঘন ঘন বাগান পরিষ্কার করা এই ছত্রাকের বিস্তারের সম্ভাবনা কমাতে সাহায্য করবে।");
        	
        	
        } else if(prediction.equals("Tomato___Bacterial_spot")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Bacterial Spot");
        	responseEntity.getBody().setDiseaseNameBn("ব্যাকটেরিয়াল স্পট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. জ্যান্থোমোনাসের চারটি প্রজাতির (এক্স. ইউভেসিকেটোরিয়া, এক্স. গার্ডনেরি, এক্স. পারফোরানস এবং এক্স. ভেসিকেটোরিয়া) রোগটি হয়ে থাকে। উত্তর ক্যারোলিনায়, X. perforans হল টমেটোতে ব্যাকটেরিয়ার দাগের সাথে যুক্ত প্রধান প্রজাতি এবং X. euvesicatoria হল মরিচের রোগের সাথে যুক্ত প্রধান প্রজাতি।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. চারটি ব্যাকটেরিয়াই কঠোরভাবে বায়বীয়, গ্রাম-নেগেটিভ রড যার একটি লম্বা চাবুকের মতো ফ্ল্যাজেলাম (লেজ) যা তাদের পানিতে চলাচল করতে দেয়, যা তাদের ভেজা উদ্ভিদের টিস্যুতে আক্রমণ করতে দেয় এবং সংক্রমণ ঘটায়।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn("<br/><br/> 1. The disease is caused by four species of Xanthomonas (X. euvesicatoria, X. gardneri, X. perforans, and X. vesicatoria). In North Carolina, X. perforans is the predominant species associated with bacterial spot on tomato and X. euvesicatoria is the predominant species associated with the disease on pepper.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. All four bacteria are strictly aerobic, gram-negative rods with a long whip-like flagellum (tail) that allows them to move in water, which allows them to invade wet plant tissue and cause infection.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("    <br/>1. The most effective management strategy is the use of pathogen-free certified seeds and disease-free transplants to prevent the introduction of the pathogen into greenhouses and field production areas. Inspect plants very carefully and reject infected transplants- including your own!\r\n"
        			+ "\r\n"
        			+ "        <br/>2. In transplant production greenhouses, minimize overwatering and handling of seedlings when they are wet.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Trays, benches, tools, and greenhouse structures should be washed and sanitized between seedlings crops.\r\n"
        			+ "        <br/>4. Do not spray, tie, harvest, or handle wet plants as that can spread the disease");
        	
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. সবচেয়ে কার্যকরী ব্যবস্থাপনার কৌশল হল প্যাথোজেন-মুক্ত প্রত্যয়িত বীজ এবং রোগ-মুক্ত প্রতিস্থাপনের ব্যবহার যাতে গ্রীনহাউস এবং মাঠ উৎপাদন এলাকায় রোগজীবাণুর প্রবেশ রোধ করা যায়। খুব সাবধানে উদ্ভিদ পরিদর্শন করুন এবং সংক্রামিত প্রতিস্থাপন প্রত্যাখ্যান করুন- আপনার নিজের সহ!\r\n"
        			+ "\r\n"
        			+ "        <br/>২. ট্রান্সপ্লান্ট উৎপাদন গ্রীনহাউসে, চারা ভেজা অবস্থায় অতিরিক্ত জল দেওয়া এবং পরিচালনা করা কমিয়ে দিন।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. ট্রে, বেঞ্চ, টুলস এবং গ্রিনহাউসের কাঠামো ধুয়ে এবং স্যানিটাইজ করা উচিত চারা ফসলের মধ্যে।\r\n"
        			+ "        <br/>৪. স্প্রে করবেন না, বাঁধবেন না, ফসল কাটাবেন না বা ভেজা গাছগুলি পরিচালনা করবেন না কারণ এটি রোগ ছড়াতে পারে");
        	
        	
        } else if(prediction.equals("Tomato___Early_blight")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Early Blight");
        	responseEntity.getBody().setDiseaseNameBn("প্রারম্ভিক ব্লাইট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. প্রাথমিক ব্লাইট দুটি ভিন্ন ঘনিষ্ঠভাবে সম্পর্কিত ছত্রাকের কারণে হতে পারে, অল্টারনারিয়া টমেটোফিলা এবং অল্টারনারিয়া সোলানি।\r\n"
        			+ "        <br/> 2. অল্টারনারিয়া টমেটোফিলা টমেটোতে এ. সোলানির চেয়ে বেশি মারাত্মক, তাই যে অঞ্চলে এ. টমেটোফিলা পাওয়া যায় সেখানে এটি টমেটোর প্রাথমিক ব্লাইটের প্রাথমিক কারণ। তবে, যদি A.tomatophila অনুপস্থিত থাকে, A.solani টমেটোতে তাড়াতাড়ি ব্লাইট সৃষ্টি করবে।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/> 1. Early blight can be caused by two different closely related fungi, Alternaria tomatophila and Alternaria solani.\r\n"
        			+ "        <br/> 2. Alternaria tomatophila is more virulent on tomato than A. solani, so in regions where A. tomatophila is found, it is the primary cause of early blight on tomato. However, if A.tomatophila is absent, A.solani will cause early blight on tomato.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/>1. Use pathogen-free seed, or collect seed only from disease-free plants..\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Rotate out of tomatoes and related crops for at least two years.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Control susceptible weeds such as black nightshade and hairy nightshade, and volunteer tomato plants throughout the rotation.\r\n"
        			+ "        <br/>4. Fertilize properly to maintain vigorous plant growth. Particularly, do not over-fertilize with potassium and maintain adequate levels of both nitrogen and phosphorus.\r\n"
        			+ "        <br/>5. Avoid working in plants when they are wet from rain, irrigation, or dew.\r\n"
        			+ "        <br/>6. Use drip irrigation instead of overhead irrigation to keep foliage dry.");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. রোগজীবাণুমুক্ত বীজ ব্যবহার করুন, অথবা শুধুমাত্র রোগমুক্ত গাছ থেকে বীজ সংগ্রহ করুন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. কমপক্ষে দুই বছরের জন্য টমেটো এবং সংশ্লিষ্ট ফসলের বাইরে ঘুরান।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. সংবেদনশীল আগাছা যেমন কালো নাইটশেড এবং লোমশ নাইটশেড নিয়ন্ত্রণ করুন এবং পুরো ঘূর্ণন জুড়ে স্বেচ্ছাসেবী টমেটো গাছ।\r\n"
        			+ "        <br/>৪. সবল উদ্ভিদ বৃদ্ধি বজায় রাখার জন্য সঠিকভাবে সার দিন। বিশেষ করে, পটাসিয়ামের সাথে অতিরিক্ত নিষিক্ত করবেন না এবং নাইট্রোজেন এবং ফসফরাস উভয়েরই পর্যাপ্ত মাত্রা বজায় রাখুন।\r\n"
        			+ "        <br/>৫. গাছপালা বৃষ্টি, সেচ বা শিশির থেকে ভেজা অবস্থায় কাজ করা এড়িয়ে চলুন।\r\n"
        			+ "        <br/>6. পাতা শুষ্ক রাখতে ওভারহেড সেচের পরিবর্তে ড্রিপ সেচ ব্যবহার করুন।");
        	
        	
        }else if(prediction.equals("Tomato___Late_blight")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Late Blight");
        	responseEntity.getBody().setDiseaseNameBn("দেরী ব্লাইট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. দেরী ব্লাইট oomycete Phytophthora infestans দ্বারা সৃষ্ট হয়। Oomycetes হল ছত্রাকের মতো জীব যাকে জলের ছাঁচও বলা হয়, কিন্তু এগুলি সত্যিকারের ছত্রাক নয়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. P. infestans এর বিভিন্ন স্ট্রেন আছে। এগুলিকে ক্লোনাল বংশ বলা হয় এবং একটি সংখ্যা কোড দ্বারা মনোনীত করা হয় (যেমন US-23)। অনেক ক্লোনাল বংশ টমেটো এবং আলু উভয়কেই প্রভাবিত করে, তবে কিছু বংশ একটি হোস্ট বা অন্যের জন্য নির্দিষ্ট।\r\n"
        			+ "        <br/> 3. হোস্ট পরিসর সাধারণত আলু এবং টমেটোর মধ্যে সীমাবদ্ধ, তবে লোমশ নাইটশেড (সোলানাম ফিজালিফোলিয়াম) একটি ঘনিষ্ঠভাবে সম্পর্কিত আগাছা যা সহজেই সংক্রামিত হতে পারে এবং রোগ বিস্তারে অবদান রাখতে পারে। আদর্শ পরিস্থিতিতে, যেমন গ্রিনহাউস, পেটুনিয়াও সংক্রমিত হতে পারে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("<br/><br/> 1. Late blight is caused by the oomycete Phytophthora infestans. Oomycetes are fungus-like organisms also called water molds, but they are not true fungi.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. There are many different strains of P. infestans. These are called clonal lineages and designated by a number code (i.e. US-23). Many clonal lineages affect both tomato and potato, but some lineages are specific to one host or the other.\r\n"
        			+ "        <br/> 3. The host range is typically limited to potato and tomato, but hairy nightshade (Solanum physalifolium) is a closely related weed that can readily become infected and may contribute to disease spread. Under ideal conditions, such as a greenhouse, petunia also may become infected.\"\"\",\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn("    Crop Rotation: Rotate tomato crops with non-host plants to reduce the buildup of the pathogen in the soil. Avoid planting tomatoes in the same location year after year.\r\n"
        			+ "\r\n"
        			+ "    Fungicide Application: Apply fungicides preventatively, especially during periods of high humidity and rainy weather. Fungicides containing copper or other approved active ingredients can help protect plants from infection.     Pruning: Remove and destroy affected leaves and branches as soon as you notice symptoms. This can help prevent the spread of the disease to healthy parts of the plant.\r\n"
        			+ "\r\n"
        			+ "    Proper Watering: Avoid overhead watering, as it can promote the spread of spores. Water at the base of the plant early in the day to allow foliage to dry before evening, reducing the humidity around the plant.");
        
        	
        	responseEntity.getBody().setPreventionOrCureBn("ফসলের ঘূর্ণন: মাটিতে রোগজীবাণু জমা কমাতে নন-হোস্ট গাছের সাথে টমেটো ফসল ঘোরান। বছরের পর বছর একই জায়গায় টমেটো রোপণ এড়িয়ে চলুন।\\r\\n\"\r\n"
        			+ "        + \"\\r\\n\"\r\n"
        			+ "        + \"ছত্রাকনাশক প্রয়োগ: প্রতিরোধমূলকভাবে ছত্রাকনাশক প্রয়োগ করুন, বিশেষ করে উচ্চ আর্দ্রতা এবং বৃষ্টির আবহাওয়ার সময়। তামা বা অন্যান্য অনুমোদিত সক্রিয় উপাদান ধারণকারী ছত্রাকনাশকগুলি গাছকে সংক্রমণ থেকে রক্ষা করতে সাহায্য করতে পারে। ছাঁটাই: লক্ষণগুলি লক্ষ্য করার সাথে সাথে আক্রান্ত পাতা এবং শাখাগুলি সরিয়ে ফেলুন এবং ধ্বংস করুন। এটি গাছের সুস্থ অংশে রোগের বিস্তার রোধ করতে সাহায্য করতে পারে৷\\r\\n\"\r\n"
        			+ "        + \"\\r\\n\"\r\n"
        			+ "        + \" সঠিক জল দেওয়া: ওভারহেড জল দেওয়া এড়িয়ে চলুন, কারণ এটি স্পোরের বিস্তারকে উত্সাহিত করতে পারে৷ দিনের প্রথম দিকে গাছের গোড়ায় জল দিন যাতে সন্ধ্যার আগে পাতাগুলি শুকিয়ে যায়, গাছের চারপাশে আর্দ্রতা হ্রাস করে৷");
        	
        	
        } else if(prediction.equals("Tomato___Leaf_Mold")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Leaf Mold");
        	responseEntity.getBody().setDiseaseNameBn("পাতার ছাঁচ");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. পাতার ছাঁচ প্যাসালোরা ফুলভা (পূর্বে ফুলভিয়া ফুলভা বা ক্লাডোস্পোরিয়াম ফুলভাম নামে পরিচিত) ছত্রাক দ্বারা সৃষ্ট হয়। টমেটো ব্যতীত অন্য কোন গাছে এটি প্যাথোজেনিক বলে জানা যায় না।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. পাতার দাগ একসাথে বেড়ে বাদামী হয়ে যায়। পাতাগুলি শুকিয়ে যায় এবং মারা যায় তবে প্রায়শই গাছের সাথে সংযুক্ত থাকে।\r\n"
        			+ "        <br/> 3. ফলের সংক্রমণ ফলের কান্ডের প্রান্তে একটি মসৃণ কালো অনিয়মিত এলাকা হিসাবে শুরু হয়। রোগের বিকাশের সাথে সাথে সংক্রামিত অঞ্চলটি ডুবে যায়, শুকনো এবং চামড়াযুক্ত হয়ে যায়।");
        	responseEntity.getBody().setCauseOfDiseaseEn("  <br/><br/> 1. Leaf mold is caused by the fungus Passalora fulva (previously called Fulvia fulva or Cladosporium fulvum). It is not known to be pathogenic on any plant other than tomato.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. Leaf spots grow together and turn brown. Leaves wither and die but often remain attached to the plant.\r\n"
        			+ "        <br/> 3. Fruit infections start as a smooth black irregular area on the stem end of the fruit. As the disease progresses, the infected area becomes sunken, dry and leathery.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/>1. Use drip irrigation and avoid watering foliage.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Space plants to provide good air movement between rows and individual plants.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Stake, string or prune to increase airflow in and around the plant.\r\n"
        			+ "        <br/>4. Sterilize stakes, ties, trellises etc. with 10 percent household bleach or commercial sanitizer.\r\n"
        			+ "        <br/>5. Circulate air in greenhouses or tunnels with vents and fans and by rolling up high tunnel sides to reduce humidity around plants.\r\n"
        			+ "        <br/>6. Keep night temperatures in greenhouses higher than outside temperatures to avoid dew formation on the foliage.\r\n"
        			+ "        <br/>7. Remove crop residue at the end of the season. Burn it or bury it away from tomato production areas.");
        	
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. ড্রিপ সেচ ব্যবহার করুন এবং পাতায় জল দেওয়া এড়িয়ে চলুন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. স্পেস গাছপালা সারি এবং পৃথক গাছপালা মধ্যে ভাল বায়ু চলাচল প্রদান.\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. গাছের ভিতরে এবং চারপাশে বায়ুপ্রবাহ বাড়াতে স্টেক, স্ট্রিং বা ছাঁটাই করুন।\r\n"
        			+ "        <br/>৪. 10 শতাংশ ঘরোয়া ব্লিচ বা বাণিজ্যিক স্যানিটাইজার দিয়ে স্টেক, টাই, ট্রেলাইজ ইত্যাদি জীবাণুমুক্ত করুন।\r\n"
        			+ "        <br/>৫. গ্রিনহাউস বা সুড়ঙ্গে ভেন্ট এবং ফ্যান দিয়ে বাতাস সঞ্চালন করুন এবং গাছের চারপাশে আর্দ্রতা কমাতে উঁচু টানেলের পাশ গুটিয়ে নিন।\r\n"
        			+ "        <br/>6. পাতায় শিশির সৃষ্টি এড়াতে গ্রিনহাউসে রাতের তাপমাত্রা বাইরের তাপমাত্রার চেয়ে বেশি রাখুন।\r\n"
        			+ "        <br/>7. মৌসুমের শেষে ফসলের অবশিষ্টাংশ অপসারণ করুন। এটিকে পুড়িয়ে ফেলুন বা টমেটো উৎপাদন এলাকা থেকে দূরে পুড়িয়ে ফেলুন।");
        	
        	
        } else if(prediction.equals("Tomato___Septoria_leaf_spot")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Leaf Spot");
        	responseEntity.getBody().setDiseaseNameBn("পাতার দাগ");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> সেপ্টোরিয়া পাতার দাগ সেপ্টোরিয়া লাইকোপারসিসি ছত্রাক দ্বারা সৃষ্ট হয়। এটি টমেটো পাতার সবচেয়ে ধ্বংসাত্মক রোগগুলির মধ্যে একটি এবং বিশেষ করে এমন অঞ্চলে গুরুতর যেখানে ভিজা, আর্দ্র আবহাওয়া বর্ধিত সময়ের জন্য থাকে।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn(" <br/><br/> Septoria leaf spot is caused by a fungus, Septoria lycopersici. It is one of the most destructive diseases of tomato foliage and is particularly severe in areas where wet, humid weather persists for extended periods.\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn("   <br/>1. Remove diseased leaves.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Improve air circulation around the plants.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Mulch around the base of the plants\r\n"
        			+ "        <br/>4. Do not use overhead watering.\r\n"
        			+ "        <br/>5. Use fungicidal sprayes.");
        	
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. রোগাক্রান্ত পাতা সরান।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. গাছপালা চারপাশে বায়ু সঞ্চালন উন্নত.\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. গাছের গোড়ার চারপাশে মাল্চ করুন\r\n"
        			+ "        <br/>৪. ওভারহেড জল ব্যবহার করবেন না.\r\n"
        			+ "        <br/>৫. ছত্রাকনাশক স্প্রে ব্যবহার করুন।");
        	
        	
        } else if(prediction.equals("Tomato___Spider_mites Two-spotted_spider_mite")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Two-spotted spider mite");
        	responseEntity.getBody().setDiseaseNameBn("দুই দাগযুক্ত স্পাইডার মাইট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. দুই দাগযুক্ত স্পাইডার মাইট হল সবচেয়ে সাধারণ মাইট প্রজাতি যা সবজি এবং ফল ফসল আক্রমণ করে।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. প্রতি বছর তাদের 20 প্রজন্ম পর্যন্ত থাকে এবং অতিরিক্ত নাইট্রোজেন এবং শুষ্ক ও ধুলোময় অবস্থার দ্বারা অনুকূল হয়।\r\n"
        			+ "        <br/> 3. প্রায়শই ব্রড-স্পেকট্রাম কীটনাশক ব্যবহারের কারণে প্রাদুর্ভাব ঘটে যা অসংখ্য প্রাকৃতিক শত্রুর সাথে হস্তক্ষেপ করে যা মাইট জনসংখ্যা পরিচালনা করতে সহায়তা করে।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn("<br/><br/> 1. The two-spotted spider mite is the most common mite species that attacks vegetable and fruit crops.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. They have up to 20 generations per year and are favored by excess nitrogen and dry and dusty conditions.\r\n"
        			+ "        <br/> 3. Outbreaks are often caused by the use of broad-spectrum insecticides which interfere with the numerous natural enemies that help to manage mite populations. ");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Avoid early season, broad-spectrum insecticide applications for other pests.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Do not over-fertilize\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Overhead irrigation or prolonged periods of rain can help reduce populations.");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. প্রারম্ভিক ঋতু, অন্যান্য কীটপতঙ্গের জন্য ব্যাপক-স্পেকট্রাম কীটনাশক প্রয়োগ এড়িয়ে চলুন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. অতিরিক্ত সার দেবেন না\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. ওভারহেড সেচ বা দীর্ঘ সময়ের বৃষ্টি জনসংখ্যা কমাতে সাহায্য করতে পারে।");
        	
        } else if(prediction.equals("Tomato___Target_Spo")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Target Spot");
        	responseEntity.getBody().setDiseaseNameBn("টার্গেট স্পট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. ছত্রাকের কারণে গাছপালা তাদের পাতা হারায়; এটি একটি বড় রোগ। ফল হওয়ার আগেই সংক্রমণ দেখা দিলে ফলন কম হয়।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. এটি প্রশান্ত মহাসাগরীয় দ্বীপের দেশগুলিতে টমেটোতে একটি সাধারণ রোগ। স্ক্রীন হাউজ ও মাঠে এ রোগ হয়।");
        	responseEntity.getBody().setCauseOfDiseaseEn("   <br/><br/> 1. The fungus causes plants to lose their leaves; it is a major disease. If infection occurs before the fruit has developed, yields are low.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. This is a common disease on tomato in Pacific island countries. The disease occurs in the screen house and in the field.");
        	
        	responseEntity.getBody().setPreventionOrCureEn(" <br/>1. Remove a few branches from the lower part of the plants to allow better airflow at the base\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Remove and burn the lower leaves as soon as the disease is seen, especially after the lower fruit trusses have been picked.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Keep plots free from weeds, as some may be hosts of the fungus.\r\n"
        			+ "        <br/>4. Do not use overhead irrigation; otherwise, it will create conditions for spore production and infection.");
        	
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. গোড়ায় ভাল বায়ুপ্রবাহের জন্য গাছের নীচের অংশ থেকে কয়েকটি শাখা সরান\r\n"
        			+ "\r\n"
        			+ "        <br/>২. রোগ দেখা দেওয়ার সাথে সাথে নীচের পাতাগুলি সরিয়ে ফেলুন এবং পুড়িয়ে ফেলুন, বিশেষ করে নীচের ফলের ট্রাসগুলি তোলার পরে।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. প্লটগুলি আগাছা থেকে মুক্ত রাখুন, কারণ কিছু ছত্রাকের হোস্ট হতে পারে।\r\n"
        			+ "        <br/>৪. ওভারহেড সেচ ব্যবহার করবেন না; অন্যথায়, এটি স্পোর উত্পাদন এবং সংক্রমণের জন্য পরিস্থিতি তৈরি করবে।");
        	
        } else if(prediction.equals("Tomato___Tomato_Yellow_Leaf_Curl_Virus")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Yellow Leaf Curl Virus");
        	responseEntity.getBody().setDiseaseNameBn("হলুদ পাতার কার্ল ভাইরাস");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. TYLCV পতঙ্গ ভেক্টর বেমিসিয়া ট্যাবাসি দ্বারা একটি অবিরাম-সঞ্চালনশীল অপ্রচারক পদ্ধতিতে প্রেরণ করা হয়। প্রাপ্তবয়স্ক পর্যায়ে ভাইরাসটি দক্ষতার সাথে প্রেরণ করা যেতে পারে।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. এই ভাইরাস সংক্রমণের একটি সংক্ষিপ্ত অধিগ্রহণ অ্যাক্সেস সময়কাল 15-20 মিনিট, এবং সুপ্ত সময়কাল 8-24 ঘন্টা।");
        	
        	responseEntity.getBody().setCauseOfDiseaseEn("   <br/><br/> 1. TYLCV is transmitted by the insect vector Bemisia tabaci in a persistent-circulative nonpropagative manner. The virus can be efficiently transmitted during the adult stages.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. This virus transmission has a short acquisition access period of 15–20 minutes, and latent period of 8–24 hours.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("  <br/>1. Currently, the most effective treatments used to control the spread of TYLCV are insecticides and resistant crop varieties.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. The effectiveness of insecticides is not optimal in tropical areas due to whitefly resistance against the insecticides; therefore, insecticides should be alternated or mixed to provide the most effective treatment against virus transmission.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Other methods to control the spread of TYLCV include planting resistant/tolerant lines, crop rotation, and breeding for resistance of TYLCV. As with many other plant viruses, one of the most promising methods to control TYLCV is the production of transgenic tomato plants resistant to TYLCV.\"\"\",\r\n"
        			+ "\r\n"
        			+ "");
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. বর্তমানে, TYLCV-এর বিস্তার নিয়ন্ত্রণের জন্য সবচেয়ে কার্যকরী চিকিৎসা হল কীটনাশক এবং প্রতিরোধী ফসলের জাত।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. কীটনাশকের বিরুদ্ধে সাদা মাছি প্রতিরোধের কারণে গ্রীষ্মমন্ডলীয় অঞ্চলে কীটনাশকের কার্যকারিতা সর্বোত্তম নয়; তাই, ভাইরাস সংক্রমণের বিরুদ্ধে সবচেয়ে কার্যকর চিকিত্সা প্রদানের জন্য কীটনাশকগুলিকে বিকল্প বা মিশ্রিত করা উচিত।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. TYLCV-এর বিস্তার নিয়ন্ত্রণের অন্যান্য পদ্ধতির মধ্যে রয়েছে রোপণ প্রতিরোধী/সহনশীল লাইন, ফসলের ঘূর্ণন, এবং TYLCV প্রতিরোধের জন্য প্রজনন। অন্যান্য অনেক উদ্ভিদ ভাইরাসের মতো, TYLCV নিয়ন্ত্রণের সবচেয়ে প্রতিশ্রুতিশীল পদ্ধতি হল TYLCV প্রতিরোধী ট্রান্সজেনিক টমেটো গাছের উৎপাদন।");
        	
        	
        }  else if(prediction.equals("Tomato___Tomato_mosaic_virus")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Tomato");
        	responseEntity.getBody().setCropNameBn("টমেটো");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Mosaic Virus");
        	responseEntity.getBody().setDiseaseNameBn("মোজাইক ভাইরাস");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("<br/><br/> 1. টমেটো মোজাইক ভাইরাস এবং তামাক মোজাইক ভাইরাস শুকনো মাটি বা পাতার ধ্বংসাবশেষে দুই বছর ধরে থাকতে পারে, তবে মাটি আর্দ্র থাকলে শুধুমাত্র এক মাস স্থায়ী হবে। ভাইরাসগুলি মাটিতে আক্রান্ত শিকড়ের ধ্বংসাবশেষেও দুই বছর পর্যন্ত বেঁচে থাকতে পারে।\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. বীজ সংক্রামিত হতে পারে এবং উদ্ভিদে ভাইরাস প্রেরণ করতে পারে তবে রোগটি সাধারণত প্রবর্তিত হয় এবং প্রাথমিকভাবে মানুষের কার্যকলাপের মাধ্যমে ছড়িয়ে পড়ে। ভাইরাসটি সহজেই শ্রমিকদের হাত, সরঞ্জাম এবং জামাকাপড়ের মধ্যে গাছের মধ্যে ছড়িয়ে পড়তে পারে যেমন গাছ বাঁধা, চুষে ফেলা এবং ফসল কাটা\r\n"
        			+ "        <br/> 3. ভাইরাসটি তামাক নিরাময় প্রক্রিয়া থেকেও বেঁচে থাকতে পারে এবং সিগারেট এবং অন্যান্য তামাকজাত দ্রব্য থেকে সিগারেটের পরে শ্রমিকদের দ্বারা পরিচালিত উদ্ভিদ উপাদানে ছড়িয়ে পড়তে পারে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("   <br/><br/> 1. Tomato mosaic virus and tobacco mosaic virus can exist for two years in dry soil or leaf debris, but will only persist one month if soil is moist. The viruses can also survive in infected root debris in the soil for up to two years.\r\n"
        			+ "\r\n"
        			+ "        <br/> 2. Seed can be infected and pass the virus to the plant but the disease is usually introduced and spread primarily through human activity. The virus can easily spread between plants on workers' hands, tools, and clothes with normal activities such as plant tying, removing of suckers, and harvest \r\n"
        			+ "        <br/> 3. The virus can even survive the tobacco curing process, and can spread from cigarettes and other tobacco products to plant material handled by workers after a cigarette\r\n"
        			+ "");
        	
        	responseEntity.getBody().setPreventionOrCureEn("  <br/>1. Purchase transplants only from reputable sources. Ask about the sanitation procedures they use to prevent disease.\r\n"
        			+ "\r\n"
        			+ "        <br/>2. Inspect transplants prior to purchase. Choose only transplants showing no clear symptoms.\r\n"
        			+ "        \r\n"
        			+ "        <br/>3. Avoid planting in fields where tomato root debris is present, as the virus can survive long-term in roots.\r\n"
        			+ "        <br/>4. Wash hands with soap and water before and during the handling of plants to reduce potential spread between plants.");
        	
        	responseEntity.getBody().setPreventionOrCureBn("<br/>1. শুধুমাত্র সম্মানজনক উৎস থেকে ট্রান্সপ্ল্যান্ট কিনুন। রোগ প্রতিরোধ করার জন্য তারা যে স্যানিটেশন পদ্ধতিগুলি ব্যবহার করে সে সম্পর্কে জিজ্ঞাসা করুন।\r\n"
        			+ "\r\n"
        			+ "        <br/>২. কেনার আগে প্রতিস্থাপন পরিদর্শন করুন। শুধুমাত্র ট্রান্সপ্লান্টগুলি বেছে নিন যার কোন স্পষ্ট লক্ষণ নেই।\r\n"
        			+ "        \r\n"
        			+ "        <br/>৩. যেখানে টমেটোর শিকড়ের ধ্বংসাবশেষ রয়েছে সেখানে রোপণ এড়িয়ে চলুন, কারণ ভাইরাসটি শিকড়গুলিতে দীর্ঘকাল বেঁচে থাকতে পারে।\r\n"
        			+ "        <br/>৪. উদ্ভিদের মধ্যে সম্ভাব্য বিস্তার কমাতে গাছ পরিচালনার আগে এবং সময় সাবান এবং জল দিয়ে হাত ধুয়ে নিন।");
        	
        	
        }     else if(prediction.equals("Rice__Brown_Spot")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Rice");
        	responseEntity.getBody().setCropNameBn("ভাত");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Brown Spot");
        	responseEntity.getBody().setDiseaseNameBn("ব্রাউন স্পট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("ছত্রাকজনিত রোগজীবাণু (বাইপোলারিস ওরিজা) : রাইস ব্রাউন স্পট মূলত ছত্রাকজনিত রোগজীবাণু বাইপোলারিস ওরিজাই দ্বারা সৃষ্ট হয়। এই রোগজীবাণু ধান গাছকে সংক্রামিত করে এবং এর ফলে পাতায় বৈশিষ্ট্যযুক্ত বাদামী থেকে গাঢ় বাদামী দাগ দেখা যায়, যা ফলনের উল্লেখযোগ্য ক্ষতির কারণ হতে পারে।\r\n"
        			+ "\r\n"
        			+ "অনুকূল পরিবেশগত অবস্থা: উচ্চ আর্দ্রতা, দীর্ঘায়িত পাতার আর্দ্রতা এবং উষ্ণ তাপমাত্রা সহ নির্দিষ্ট পরিবেশগত অবস্থার দ্বারা রোগের বিকাশ অনুকূল হয়। এই অবস্থাগুলি ধান গাছে ছত্রাকের স্পোরগুলির জন্য একটি অনুকূল পরিবেশ তৈরি করে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("Fungal Pathogen (Bipolaris oryzae): Rice Brown Spot is primarily caused by the fungal pathogen Bipolaris oryzae. This pathogen infects rice plants and results in the characteristic brown to dark brown spots on the leaves, which can lead to significant yield losses.\r\n"
        			+ "\r\n"
        			+ "Favorable Environmental Conditions: The disease development is favored by specific environmental conditions, including high humidity, prolonged leaf wetness, and warm temperatures. These conditions create a conducive environment for the fungal spores to infect rice plants.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("Prevention:\r\n"
        			+ "\r\n"
        			+ "    Crop Rotation: Implement crop rotation practices to break the disease cycle. Avoid planting rice in the same field consecutively. By rotating rice with non-host crops, you can reduce the buildup of the fungal pathogen Bipolaris oryzae in the soil, which helps prevent the recurrence of Rice Brown Spot.\r\n"
        			+ "\r\n"
        			+ "Cure:\r\n"
        			+ "\r\n"
        			+ "    Fungicide Application: If Rice Brown Spot is detected in your rice crop, consider applying appropriate fungicides as a curative measure. Fungicides containing active ingredients effective against the pathogen can help control the disease and prevent its further spread. It's essential to follow recommended application rates and timings for effective control.\r\n"
        			+ "\r\n"
        			+ "Please note that prevention measures are generally more effective in managing Rice Brown Spot. Timely detection and implementation of preventive strategies are crucial for disease management in rice cultivation.");
        	responseEntity.getBody().setPreventionOrCureBn("প্রতিরোধ:\r\n"
        			+ "\r\n"
        			+ "    ফসলের ঘূর্ণন: রোগের চক্র ভাঙতে শস্য ঘূর্ণন অনুশীলন প্রয়োগ করুন। একই জমিতে পরপর ধান রোপণ করা থেকে বিরত থাকুন। নন-হোস্ট শস্যের সাথে ধান ঘুরিয়ে, আপনি মাটিতে বাইপোলারিস ওরিজা নামক ছত্রাকের জীবাণুর গঠন কমাতে পারেন, যা রাইস ব্রাউন স্পটের পুনরাবৃত্তি রোধ করতে সাহায্য করে।\r\n"
        			+ "\r\n"
        			+ "নিরাময়:\r\n"
        			+ "\r\n"
        			+ "    ছত্রাকনাশক প্রয়োগ: আপনার ধানের ফসলে যদি রাইস ব্রাউন স্পট সনাক্ত করা হয়, তাহলে একটি নিরাময়মূলক পরিমাপ হিসাবে উপযুক্ত ছত্রাকনাশক প্রয়োগ করার কথা বিবেচনা করুন। প্যাথোজেনের বিরুদ্ধে কার্যকর সক্রিয় উপাদান সম্বলিত ছত্রাকনাশক রোগ নিয়ন্ত্রণে সাহায্য করতে পারে এবং এর আরও বিস্তার রোধ করতে পারে। কার্যকর নিয়ন্ত্রণের জন্য প্রস্তাবিত আবেদনের হার এবং সময়গুলি অনুসরণ করা অপরিহার্য।\r\n"
        			+ "\r\n"
        			+ "অনুগ্রহ করে মনে রাখবেন যে প্রতিরোধ ব্যবস্থা সাধারণত রাইস ব্রাউন স্পট পরিচালনায় আরও কার্যকর। সময়মত সনাক্তকরণ এবং প্রতিরোধমূলক কৌশল বাস্তবায়ন ধান চাষে রোগ ব্যবস্থাপনার জন্য অত্যন্ত গুরুত্বপূর্ণ।");
        	
        	
        } else if(prediction.equals("Rice___Healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Rice");
        	responseEntity.getBody().setCropNameBn("ভাত");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        	
        } else if(prediction.equals("Rice___Leaf_Blast")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Rice");
        	responseEntity.getBody().setCropNameBn("ভাত");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Leaf Blast");
        	responseEntity.getBody().setDiseaseNameBn("পাতার বিস্ফোরণ");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("রাইস লিফ ব্লাস্ট এর কারণ প্রাথমিকভাবে ম্যাগনাপোর্থ ওরিজা নামে পরিচিত একটি ছত্রাকের রোগজীবাণু। এই ছত্রাকটি ধান গাছকে সংক্রমিত করে, যার ফলে পাতা, কান্ড এবং কখনও কখনও প্যানিকলে বৈশিষ্ট্যগত ক্ষত বা ব্লাস্ট হয়। অনুকূল পরিবেশগত অবস্থা যেমন উচ্চ আর্দ্রতা, উষ্ণ তাপমাত্রা এবং স্থায়ী জল এই রোগের বিকাশ এবং বিস্তারকে উন্নীত করতে পারে। ছত্রাক স্পোর তৈরি করে যা বাতাস বা পানির মাধ্যমে বহন করা যায়, যা ধানের ক্ষেতে রোগের দ্রুত বিস্তারকে সহজতর করে। সঠিক রোগ ব্যবস্থাপনা অনুশীলন এবং প্রতিরোধী ধানের জাত ব্যবহার ধানের পাতার ব্লাস্ট নিয়ন্ত্রণের জন্য অপরিহার্য।");
        	responseEntity.getBody().setCauseOfDiseaseEn("The cause of \"Rice Leaf Blast\" is primarily a fungal pathogen known as Magnaporthe oryzae. This fungus infects rice plants, causing characteristic lesions or \"blast\" on the leaves, stems, and sometimes panicles. Favorable environmental conditions such as high humidity, warm temperatures, and standing water can promote the development and spread of this disease. The fungus produces spores that can be carried by wind or water, facilitating the rapid spread of the disease within rice fields. Proper disease management practices and the use of resistant rice varieties are essential to control Rice Leaf Blast.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("Prevention:\r\n"
        			+ "\r\n"
        			+ "    Use Resistant Varieties: Plant rice varieties that are resistant or tolerant to Rice Leaf Blast. Resistant varieties are less susceptible to the disease and can help reduce the risk of infection. Consult with local agricultural experts or extension services to identify and choose suitable resistant varieties for your region.\r\n"
        			+ "\r\n"
        			+ "Cure:\r\n"
        			+ "\r\n"
        			+ "    Fungicide Application: If Rice Leaf Blast is detected in your rice crop, consider applying appropriate fungicides as a curative measure. Fungicides containing active ingredients effective against Magnaporthe oryzae can help control the disease and limit its further spread. Follow recommended application rates, timings, and safety precautions when using fungicides.");
        	responseEntity.getBody().setPreventionOrCureBn("প্রতিরোধ:\r\n"
        			+ "\r\n"
        			+ "    প্রতিরোধী জাত ব্যবহার করুন: ধানের পাতার বিস্ফোরণ প্রতিরোধী বা সহনশীল ধানের জাত রোপণ করুন। প্রতিরোধী জাতগুলি রোগের জন্য কম সংবেদনশীল এবং সংক্রমণের ঝুঁকি কমাতে সাহায্য করতে পারে। আপনার অঞ্চলের জন্য উপযুক্ত প্রতিরোধী জাত সনাক্ত করতে এবং চয়ন করতে স্থানীয় কৃষি বিশেষজ্ঞ বা সম্প্রসারণ পরিষেবাগুলির সাথে পরামর্শ করুন।\r\n"
        			+ "\r\n"
        			+ "নিরাময়:\r\n"
        			+ "\r\n"
        			+ "    ছত্রাকনাশক প্রয়োগ: যদি আপনার ধানের ফসলে ধানের পাতার বিস্ফোরণ সনাক্ত করা হয়, তাহলে একটি নিরাময়মূলক ব্যবস্থা হিসাবে উপযুক্ত ছত্রাকনাশক প্রয়োগ করার কথা বিবেচনা করুন। Magnaporthe oryzae-এর বিরুদ্ধে কার্যকরী সক্রিয় উপাদান ধারণকারী ছত্রাকনাশক রোগ নিয়ন্ত্রণে সাহায্য করতে পারে এবং এর আরও বিস্তার সীমিত করতে পারে। ছত্রাকনাশক ব্যবহার করার সময় সুপারিশকৃত প্রয়োগের হার, সময় এবং নিরাপত্তা সতর্কতা অনুসরণ করুন।");
        }   else if(prediction.equals("Rice___Neck_Blast")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Rice");
        	responseEntity.getBody().setCropNameBn("ভাত");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Neck_Blast");
        	responseEntity.getBody().setDiseaseNameBn("নেক_ব্লাস্ট");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("\"রাইস নেক ব্লাস্ট\" এর কারণ হল ছত্রাকের প্যাথোজেন ম্যাগনাপোর্থ ওরিজা, যেটি রাইস লিফ ব্লাস্টের জন্য দায়ী একই প্যাথোজেন। এই ছত্রাকটি ধান গাছের ঘাড় বা প্যানিকেল সহ ধানের গাছকে সংক্রমিত করে, যার ফলে ঘাড়ের অংশে বৈশিষ্ট্যগত ক্ষত এবং ক্ষতি হয়। অনুকূল পরিবেশগত অবস্থা, যেমন উচ্চ আর্দ্রতা, উষ্ণ তাপমাত্রা এবং স্থায়ী জল এই রোগের বিকাশ এবং বিস্তারকে উৎসাহিত করে। রাইস লিফ ব্লাস্টের মতো, রাইস নেক ব্লাস্ট সঠিকভাবে পরিচালনা না করলে ফলন ক্ষতি হতে পারে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("The cause of \"Rice Neck Blast\" is also the fungal pathogen Magnaporthe oryzae, which is the same pathogen responsible for Rice Leaf Blast. This fungus infects rice plants, including the neck or panicle of the plant, leading to characteristic lesions and damage in the neck area. Favorable environmental conditions, such as high humidity, warm temperatures, and standing water, promote the development and spread of this disease. Similar to Rice Leaf Blast, Rice Neck Blast can result in yield losses if not properly managed.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("Prevention:\r\n"
        			+ "\r\n"
        			+ "    Crop Rotation and Resistant Varieties: Implement crop rotation practices and choose rice varieties that are resistant or tolerant to both Rice Leaf Blast and Rice Neck Blast. Crop rotation helps break the disease cycle, and resistant/tolerant varieties are less susceptible to the pathogen, reducing the risk of infection.\r\n"
        			+ "\r\n"
        			+ "Cure:\r\n"
        			+ "\r\n"
        			+ "    Fungicide Application: If Rice Neck Blast is detected in your rice crop, consider applying appropriate fungicides as a curative measure. Fungicides containing active ingredients effective against Magnaporthe oryzae can help control the disease and limit its spread within the crop. Follow recommended application rates, timings, and safety guidelines when using fungicides.");
        	responseEntity.getBody().setPreventionOrCureBn("প্রতিরোধ:\r\n"
        			+ "\r\n"
        			+ "    ফসলের ঘূর্ণন এবং প্রতিরোধী জাত: শস্য ঘূর্ণন অনুশীলনগুলি বাস্তবায়ন করুন এবং ধানের জাতগুলি বেছে নিন যা ধানের পাতার বিস্ফোরণ এবং ধানের ঝাড়ের ব্লাস্ট উভয়ের জন্য প্রতিরোধী বা সহনশীল। ফসলের ঘূর্ণন রোগের চক্র ভাঙতে সাহায্য করে এবং প্রতিরোধী/সহনশীল জাতগুলি রোগজীবাণুর প্রতি কম সংবেদনশীল, সংক্রমণের ঝুঁকি কমায়।\r\n"
        			+ "\r\n"
        			+ "নিরাময়:\r\n"
        			+ "\r\n"
        			+ "    ছত্রাকনাশক প্রয়োগ: আপনার ধানের ফসলে যদি রাইস নেক ব্লাস্ট ধরা পড়ে, তাহলে নিরাময়মূলক ব্যবস্থা হিসাবে উপযুক্ত ছত্রাকনাশক প্রয়োগ করার কথা বিবেচনা করুন। Magnaporthe oryzae-এর বিরুদ্ধে কার্যকরী সক্রিয় উপাদান সম্বলিত ছত্রাকনাশক রোগ নিয়ন্ত্রণে সাহায্য করতে পারে এবং ফসলের মধ্যে এর বিস্তার সীমিত করতে পারে। ছত্রাকনাশক ব্যবহার করার সময় সুপারিশকৃত প্রয়োগের হার, সময় এবং নিরাপত্তা নির্দেশিকা অনুসরণ করুন।");
        } else if(prediction.equals("Wheat___Healthy")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Wheat");
        	responseEntity.getBody().setCropNameBn("গম");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("No disease");
        	responseEntity.getBody().setDiseaseNameBn("কোন রোগ নেই");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("");
        	responseEntity.getBody().setCauseOfDiseaseEn("");
        	
        	responseEntity.getBody().setPreventionOrCureEn("<br/><br/> Don't worry. Your crop is healthy. Keep it up !!!");
        	responseEntity.getBody().setPreventionOrCureBn("<br/><br/> চিন্তা করবেন না। আপনার ফসল স্বাস্থ্যকর। এটা বজায় রাখুন!!!");
        	
        } else if(prediction.equals("Wheat___Brown_Rust")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Wheat");
        	responseEntity.getBody().setCropNameBn("গম");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Brown Rust");
        	responseEntity.getBody().setDiseaseNameBn("বাদামী মরিচা");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("\"গমের বাদামী মরিচা\" এর কারণ হল পুকিনিয়া ট্রিটিসিনা নামে পরিচিত একটি ছত্রাকের রোগজীবাণু। এই রোগজীবাণু বিশেষভাবে গম গাছকে সংক্রামিত করে, যার ফলে গম গাছের পাতা, ডালপালা এবং মাটির ওপরের অন্যান্য অংশে বাদামি থেকে লালচে-বাদামী মরিচা পুঁজ বা ক্ষত দেখা দেয়। গমের বাদামী মরিচা সঠিকভাবে ব্যবস্থাপনা না করলে ফলন উল্লেখযোগ্যভাবে ক্ষতির কারণ হতে পারে। এই রোগটি আর্দ্র এবং শীতল থেকে মাঝারিভাবে উষ্ণ আবহাওয়ার দ্বারা অনুকূল হয়, যা এই ধরনের পরিবেশগত অবস্থার সাথে অঞ্চলে উদ্বেগের কারণ হয়ে ওঠে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("The cause of \"Wheat Brown Rust\" is a fungal pathogen known as Puccinia triticina. This pathogen specifically infects wheat plants, leading to the development of characteristic brown to reddish-brown rust pustules or lesions on the leaves, stems, and other above-ground parts of the wheat plant. Wheat Brown Rust can cause significant yield losses if not properly managed. The disease is favored by humid and cool to moderately warm weather conditions, making it a concern in regions with such environmental conditions.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("Prevention:\r\n"
        			+ "\r\n"
        			+ "    Plant Resistant Varieties: Choose wheat varieties that are known to be resistant or tolerant to Wheat Brown Rust. Resistant varieties are less susceptible to the disease and can significantly reduce the risk of infection. Consult with local agricultural experts or extension services to identify and select suitable resistant wheat varieties for your region.\r\n"
        			+ "\r\n"
        			+ "Cure:\r\n"
        			+ "\r\n"
        			+ "    Fungicide Application: If Wheat Brown Rust is detected in your wheat crop, consider applying appropriate fungicides as a curative measure. Fungicides containing active ingredients effective against Puccinia triticina can help control the disease and prevent its further spread. Follow recommended application rates, timings, and safety precautions when using fungicides.");
        	responseEntity.getBody().setPreventionOrCureBn("প্রতিরোধ:\r\n"
        			+ "\r\n"
        			+ "    উদ্ভিদ প্রতিরোধী জাত: গমের জাতগুলি বেছে নিন যা গমের বাদামী মরিচা প্রতিরোধী বা সহনশীল বলে পরিচিত। প্রতিরোধী জাতগুলি রোগের প্রতি কম সংবেদনশীল এবং উল্লেখযোগ্যভাবে সংক্রমণের ঝুঁকি কমাতে পারে। আপনার অঞ্চলের জন্য উপযুক্ত প্রতিরোধী গমের জাত সনাক্ত করতে এবং নির্বাচন করতে স্থানীয় কৃষি বিশেষজ্ঞ বা সম্প্রসারণ পরিষেবাগুলির সাথে পরামর্শ করুন।\r\n"
        			+ "\r\n"
        			+ "নিরাময়:\r\n"
        			+ "\r\n"
        			+ "    ছত্রাকনাশক প্রয়োগ: যদি আপনার গমের ফসলে গমের বাদামী মরিচা ধরা পড়ে তবে একটি নিরাময়মূলক ব্যবস্থা হিসাবে উপযুক্ত ছত্রাকনাশক প্রয়োগ করার কথা বিবেচনা করুন। Puccinia triticina এর বিরুদ্ধে কার্যকরী সক্রিয় উপাদান সম্বলিত ছত্রাকনাশক রোগ নিয়ন্ত্রণে সাহায্য করতে পারে এবং এর আরও বিস্তার রোধ করতে পারে। ছত্রাকনাশক ব্যবহার করার সময় সুপারিশকৃত প্রয়োগের হার, সময় এবং নিরাপত্তা সতর্কতা অনুসরণ করুন।");
      
        }   else if(prediction.equals("Wheat___Yellow_Rust")) {
        	
        	
        	responseEntity.getBody().setCropNameEn("Wheat");
        	responseEntity.getBody().setCropNameBn("গম");
        	
        	
        	responseEntity.getBody().setDiseaseNameEn("Yellow Rust");
        	responseEntity.getBody().setDiseaseNameBn("হলুদ মরিচা");
        	
        	responseEntity.getBody().setCauseOfDiseaseBn("\"গমের হলুদ মরিচা\" এর কারণ হল আরেকটি ছত্রাকের প্যাথোজেন, Puccinia striiformis f। sp tritici এই ছত্রাকজনিত রোগজীবাণু বিশেষভাবে গম গাছকে সংক্রামিত করে, যার ফলে গম গাছের পাতা, কান্ড এবং মাটির উপরিভাগের অন্যান্য অংশে হলুদ থেকে কমলা-হলুদ মরিচা পুঁজ বা ক্ষত দেখা দেয়। গমের হলুদ মরিচা কার্যকরভাবে পরিচালনা না করলে ফলন উল্লেখযোগ্যভাবে ক্ষতির কারণ হতে পারে। এই রোগটি শীতল থেকে মাঝারি উষ্ণ তাপমাত্রার অনুকূল এবং অনুকূল পরিস্থিতিতে দ্রুত ছড়িয়ে পড়তে পারে।");
        	responseEntity.getBody().setCauseOfDiseaseEn("The cause of \"Wheat Yellow Rust\" is another fungal pathogen, Puccinia striiformis f. sp. tritici. This fungal pathogen specifically infects wheat plants, leading to the development of characteristic yellow to orange-yellow rust pustules or lesions on the leaves, stems, and other above-ground parts of the wheat plant. Wheat Yellow Rust can result in significant yield losses if not effectively managed. The disease is favored by cool to moderately warm temperatures and can spread rapidly under conducive conditions.");
        	
        	responseEntity.getBody().setPreventionOrCureEn("Prevention:\r\n"
        			+ "\r\n"
        			+ "    Plant Resistant Varieties: Select wheat varieties that are known to be resistant or tolerant to Wheat Yellow Rust. Resistant varieties have built-in resistance mechanisms that make them less susceptible to the disease. Planting resistant varieties is one of the most effective ways to prevent Wheat Yellow Rust.\r\n"
        			+ "\r\n"
        			+ "Cure:\r\n"
        			+ "\r\n"
        			+ "    Fungicide Application: If Wheat Yellow Rust is detected in your wheat crop, consider applying appropriate fungicides as a curative measure. Fungicides containing active ingredients effective against Puccinia striiformis f. sp. tritici can help control the disease and prevent its further spread within the crop. Follow recommended application rates, timings, and safety guidelines when using fungicides.");
        	responseEntity.getBody().setPreventionOrCureBn("প্রতিরোধ:\r\n"
        			+ "\r\n"
        			+ "    উদ্ভিদ প্রতিরোধী জাত: গমের হলুদ মরিচা প্রতিরোধী বা সহনশীল বলে পরিচিত গমের জাত নির্বাচন করুন। প্রতিরোধী জাতগুলির অন্তর্নির্মিত প্রতিরোধ ব্যবস্থা রয়েছে যা তাদের রোগের প্রতি কম সংবেদনশীল করে তোলে। প্রতিরোধী জাত রোপণ করা গমের হলুদ মরিচা প্রতিরোধের অন্যতম কার্যকর উপায়।\r\n"
        			+ "\r\n"
        			+ "নিরাময়:\r\n"
        			+ "\r\n"
        			+ "    ছত্রাকনাশক প্রয়োগ: আপনার গমের ফসলে যদি গমের হলুদ মরিচা ধরা পড়ে তবে একটি নিরাময়মূলক ব্যবস্থা হিসাবে উপযুক্ত ছত্রাকনাশক প্রয়োগ করার কথা বিবেচনা করুন। সক্রিয় উপাদান ধারণকারী ছত্রাকনাশক Puccinia striiformis f এর বিরুদ্ধে কার্যকর। sp ট্রিটিসি রোগ নিয়ন্ত্রণে সাহায্য করতে পারে এবং ফসলের মধ্যে এর আরও বিস্তার রোধ করতে পারে। ছত্রাকনাশক ব্যবহার করার সময় সুপারিশকৃত প্রয়োগের হার, সময় এবং নিরাপত্তা নির্দেশিকা অনুসরণ করুন।");
        	
        }
        
        
        
        
        
        
       
        responseEntity.getBody().setMessage("Service Provided");
        responseEntity.getBody().setWallet(userServices.getWalletUser(uid));
        
	      
	
	    
		return responseEntity.getBody();
	}
}
