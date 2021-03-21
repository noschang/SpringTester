-------------------------------------------------------
-- Table creation DDL
-------------------------------------------------------

CREATE TABLE "POST" (
    "ID" NUMBER(19, 0) CONSTRAINT "NN_#POST_$ID" NOT NULL ENABLE,
	"UUID" RAW(16) CONSTRAINT "NN_#POST_$UUID" NOT NULL ENABLE,
    "ENTITY_VERSION" NUMBER(19, 0) CONSTRAINT "NN_#POST_$ENTVER" NOT NULL ENABLE,
    "TITLE" VARCHAR2(128 CHAR) CONSTRAINT "NN_#POST_$TITLE" NOT NULL ENABLE,
    "CONTENT" VARCHAR2(256 CHAR) CONSTRAINT "NN_#POST_$CONT" NOT NULL ENABLE,
    "PROPERTIES" VARCHAR2(1024 CHAR),
    CONSTRAINT "PK_#POST_$ID" PRIMARY KEY ("ID") ENABLE
);

-------------------------------------------------------
-- Table constraints
-------------------------------------------------------

-- Check constraint for ID. Ranges from 1 to JAVA Long.MAX_VALUE
ALTER TABLE "POST" ADD CONSTRAINT "CHK_#POST_$ID" CHECK ("ID" BETWEEN 1 AND 9223372036854775807) ENABLE;

-- Check constraint for UUID. The length must be exactly 16, but the "LENGTH" function
-- always returns the value doubled, so we're checking for length = 32
ALTER TABLE "POST" ADD CONSTRAINT "CHK_#POST_$UUID" CHECK (LENGTH("UUID") = 32) ENABLE;

-- Unique constraint for UUID
ALTER TABLE "POST" ADD CONSTRAINT "UK_#POST_$UUID" UNIQUE ("UUID") ENABLE;

-- Check constraint for ENTITY_VERSION. Ranges from 0 to JAVA Long.MAX_VALUE
ALTER TABLE "POST" ADD CONSTRAINT "CHK_#POST_$ENTVER" CHECK ("ENTITY_VERSION" BETWEEN 0 AND 9223372036854775807) ENABLE;

-------------------------------------------------------
-- Indexes
-------------------------------------------------------

-- Index for searchs
CREATE INDEX "IDX_#POST_$SEARCH" ON "POST"("ENTITY_VERSION","TITLE","CONTENT");

-------------------------------------------------------
-- Data insertion
-------------------------------------------------------

INSERT INTO "POST" ("ID","UUID","ENTITY_VERSION","TITLE","CONTENT","PROPERTIES")
	SELECT 1,HEXTORAW('3101D69B39B84261B30BD57FB983D5F9'),0,'Test post #1','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 2,HEXTORAW('6D29AD6EA87B4877A7DC26AC57110EC8'),0,'Test post #2','This is a test post created on 14/07/2020','{"likes":1348,"dislikes":2558,"views":4181}' FROM dual UNION ALL 
	SELECT 3,HEXTORAW('1FF170DD288444B7BE2EFDD57B81F6A1'),0,'Test post #3','This is a test post created on 14/07/2020','{"likes":4848,"dislikes":288,"views":9653}' FROM dual UNION ALL 
	SELECT 4,HEXTORAW('2B1153D4487F4F32B24640834D1B3C3E'),0,'Test post #4','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 5,HEXTORAW('F5E8C720885147CB94DE807E1B9134DD'),0,'Test post #5','This is a test post created on 14/07/2020','{"likes":3924,"dislikes":141,"views":8578}' FROM dual UNION ALL 
	SELECT 6,HEXTORAW('71298055D5B94C3EAC4E83BD2E9404A3'),0,'Test post #6','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 7,HEXTORAW('404A207263394D1F8E05A84564DB163B'),0,'Test post #7','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 8,HEXTORAW('C1A9B785BCE54383BE234A233765770F'),0,'Test post #8','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 9,HEXTORAW('981EDD7986764018A7028E70F30E75D6'),0,'Test post #9','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 10,HEXTORAW('17832B88E0E24E9897AE9ECC2BB04341'),0,'Test post #10','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 11,HEXTORAW('B016D082953B4992B99074AC006DF94B'),0,'Test post #11','This is a test post created on 14/07/2020','{"likes":1148,"dislikes":2258,"views":3881}' FROM dual UNION ALL 
	SELECT 12,HEXTORAW('35A73A42078741D2929BEC04C775A1D6'),0,'Test post #12','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 13,HEXTORAW('61A775FEC685417E9788C18F50FDFB27'),0,'Test post #13','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 14,HEXTORAW('A68B2311DBA84BB1B70B2F51714C78B1'),0,'Test post #14','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 15,HEXTORAW('996FE76E7E4D4613BE0F4FA243413C9A'),0,'Test post #15','This is a test post created on 14/07/2020','{"likes":3412,"dislikes":1659,"views":9948}' FROM dual UNION ALL 
	SELECT 16,HEXTORAW('C513152AB86B40C4BA3B66E2BD4B6676'),0,'Test post #16','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 17,HEXTORAW('DD47C23CAA604D11916A660FE067E65A'),0,'Test post #17','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 18,HEXTORAW('558D8A2D1159467F89B43DC1413017F1'),0,'Test post #18','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 19,HEXTORAW('5F4E656552514B48A1D37C771E841C56'),0,'Test post #19','This is a test post created on 14/07/2020','{"likes":119,"dislikes":4202,"views":9180}' FROM dual UNION ALL 
	SELECT 20,HEXTORAW('B9587971B6714D56A521352D2B549CC4'),0,'Test post #20','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 21,HEXTORAW('8FA5345739D4465BB4544FBA75588BD9'),0,'Test post #21','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 22,HEXTORAW('C1E7EDA47A324A3CB284612CBFADCA5F'),0,'Test post #22','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 23,HEXTORAW('1B8001391FE24EB0BF50256D0B097D2C'),0,'Test post #23','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 24,HEXTORAW('15AFBA108109441F8C6CFA8CFEEE5ED7'),0,'Test post #24','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 25,HEXTORAW('78BFBA77BD794E60B3FE4A03469CB021'),0,'Test post #25','This is a test post created on 14/07/2020','{"likes":3013,"dislikes":2310,"views":10081}' FROM dual UNION ALL 
	SELECT 26,HEXTORAW('BF9AAE0BD6664E1AA5CEF9E658A6E638'),0,'Test post #26','This is a test post created on 14/07/2020','{"likes":2226,"dislikes":3379,"views":8058}' FROM dual UNION ALL 
	SELECT 27,HEXTORAW('A48FC06EE96B4BF087793FABA9D36EF1'),0,'Test post #27','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 28,HEXTORAW('36692AE944E34E97AEECD98C6651725B'),0,'Test post #28','This is a test post created on 14/07/2020','{"likes":4967,"dislikes":1104,"views":6832}' FROM dual UNION ALL 
	SELECT 29,HEXTORAW('1685D3F7E8A144B3A9D6668B4DE44468'),0,'Test post #29','This is a test post created on 14/07/2020','{"likes":246,"dislikes":3194,"views":7651}' FROM dual UNION ALL 
	SELECT 30,HEXTORAW('5FF121FB15FB465DB90BBDC22F0B2270'),0,'Test post #30','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 31,HEXTORAW('43AB8722C9724A449A7F70C1FA39FAE8'),0,'Test post #31','This is a test post created on 14/07/2020','{"likes":186,"dislikes":1242,"views":5718}' FROM dual UNION ALL 
	SELECT 32,HEXTORAW('A5DAB3F08BB140988789350B5A39F075'),0,'Test post #32','This is a test post created on 14/07/2020','{"likes":1784,"dislikes":3347,"views":9587}' FROM dual UNION ALL 
	SELECT 33,HEXTORAW('FF413884206E4068B98AF605A8D3BF2D'),0,'Test post #33','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 34,HEXTORAW('3CE17CDF19BB42C19795930E517FC552'),0,'Test post #34','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 35,HEXTORAW('A0353A78C0844F67A7D321F97178B954'),0,'Test post #35','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 36,HEXTORAW('536417F101BB4E9DB0D4B04ECD61E793'),0,'Test post #36','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 37,HEXTORAW('A0C2486FFF564CB2B2BF2C18FDD572AF'),0,'Test post #37','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 38,HEXTORAW('4CD437269F2B4B2C94778EB0FB8F6089'),0,'Test post #38','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 39,HEXTORAW('4F6DB84075094988A08BF5C8B08FB890'),0,'Test post #39','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 40,HEXTORAW('DCFEC111DE214BB4923B5183199767BC'),0,'Test post #40','This is a test post created on 14/07/2020','{"likes":3005,"dislikes":4972,"views":12303}' FROM dual UNION ALL 
	SELECT 41,HEXTORAW('A99C64350B9C41B8BB161F834BC555CF'),0,'Test post #41','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 42,HEXTORAW('3AB4C7A115D34F5AAE190E6B5695057B'),0,'Test post #42','This is a test post created on 14/07/2020','{"likes":292,"dislikes":1850,"views":4326}' FROM dual UNION ALL 
	SELECT 43,HEXTORAW('2DE7E577189443F8A01B24BF55F0A116'),0,'Test post #43','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 44,HEXTORAW('ABE0F66D05A44E99BA8981114E2351D2'),0,'Test post #44','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 45,HEXTORAW('A209EA685CE342F58EDFBD94C6FCF400'),0,'Test post #45','This is a test post created on 14/07/2020','{"likes":2105,"dislikes":1814,"views":5564}' FROM dual UNION ALL 
	SELECT 46,HEXTORAW('FFC613C0FD074DDB82C92DADF1510D7F'),0,'Test post #46','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 47,HEXTORAW('20D5C43B9EC44C15BE106F05E330266C'),0,'Test post #47','This is a test post created on 14/07/2020','{"likes":2762,"dislikes":319,"views":5359}' FROM dual UNION ALL 
	SELECT 48,HEXTORAW('459D447136C844DA9529570F786C0D5D'),0,'Test post #48','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 49,HEXTORAW('022E6F6E2D354164B8FF4A5EE5F29923'),0,'Test post #49','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 50,HEXTORAW('CB3FBC182E5543EDBEB761CB6D963E9E'),0,'Test post #50','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 51,HEXTORAW('6DE3570FF3544C55A6B7DBD2868AC16E'),0,'Test post #51','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 52,HEXTORAW('8A700C31DCA3426999E4F4F4B33402E4'),0,'Test post #52','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 53,HEXTORAW('A2C0C85C89A841A592B41A7E7A5F1F47'),0,'Test post #53','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 54,HEXTORAW('ED292320F4734044AB857C284DF884CA'),0,'Test post #54','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 55,HEXTORAW('5F571A0AB47641DBB19BD194B1145863'),0,'Test post #55','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 56,HEXTORAW('09D64944D4454E06A3D0DB7808F2F6E8'),0,'Test post #56','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 57,HEXTORAW('717CD7308E0F460B947149C603641285'),0,'Test post #57','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 58,HEXTORAW('D7251EB2BFAC47499B906BD283174183'),0,'Test post #58','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 59,HEXTORAW('57A0814BF96C41759020EDFD96958030'),0,'Test post #59','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 60,HEXTORAW('ED77F558460846C8813336D1F0987CFD'),0,'Test post #60','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 61,HEXTORAW('4664C73B1BB94ED28B68FF17D694CCFC'),0,'Test post #61','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 62,HEXTORAW('20722C1454C84F91AB4D3A9815CDEAAB'),0,'Test post #62','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 63,HEXTORAW('637F6D5122854BDAA19399B78DD2303C'),0,'Test post #63','This is a test post created on 14/07/2020','{"likes":1326,"dislikes":494,"views":3968}' FROM dual UNION ALL 
	SELECT 64,HEXTORAW('CE1E851A786F42B5A3032E9A6AC1AD79'),0,'Test post #64','This is a test post created on 14/07/2020','{"likes":387,"dislikes":622,"views":4836}' FROM dual UNION ALL 
	SELECT 65,HEXTORAW('43597F37291E4B53A9856C75A07DF8DB'),0,'Test post #65','This is a test post created on 14/07/2020','{"likes":1030,"dislikes":899,"views":6164}' FROM dual UNION ALL 
	SELECT 66,HEXTORAW('A5754EB7B2324672B1849962C96B5192'),0,'Test post #66','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 67,HEXTORAW('74D97D24B67C48A7A386397416D0B138'),0,'Test post #67','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 68,HEXTORAW('214950DA09924382B8B097C0D13DEFF2'),0,'Test post #68','This is a test post created on 14/07/2020','{"likes":663,"dislikes":1293,"views":5400}' FROM dual UNION ALL 
	SELECT 69,HEXTORAW('0A7BB8F2E08249248E70C0915E057D2B'),0,'Test post #69','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 70,HEXTORAW('E16DF6A5394F414ABE6FAC992C940566'),0,'Test post #70','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 71,HEXTORAW('E176C497B4744FA5B414ADB8A4EED096'),0,'Test post #71','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 72,HEXTORAW('C288D4728C704332B55F8367291ACE99'),0,'Test post #72','This is a test post created on 14/07/2020','{"likes":3450,"dislikes":3691,"views":12085}' FROM dual UNION ALL 
	SELECT 73,HEXTORAW('72B11086C9E940C3B05B65DCD1BFDC43'),0,'Test post #73','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 74,HEXTORAW('8F9E21768D074FE5AF40D9042D2411A8'),0,'Test post #74','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 75,HEXTORAW('D1BDBE33BD864AC0BC99EE0086B9AF39'),0,'Test post #75','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 76,HEXTORAW('469421DE7C2543C69FF0267657ABE8F1'),0,'Test post #76','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 77,HEXTORAW('9F70D4977EF5467493545540C12A24DC'),0,'Test post #77','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 78,HEXTORAW('732266AD9F534C23887177F48DBA9081'),0,'Test post #78','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 79,HEXTORAW('0D2485C078014EF09736BDE4C7B67C40'),0,'Test post #79','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 80,HEXTORAW('E90C42CC19114E1A8E2543A1D1991C4B'),0,'Test post #80','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 81,HEXTORAW('1BEF7EFF4340464594180C2F1349C286'),0,'Test post #81','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 82,HEXTORAW('4D443428973943A8BD24DB5AE95A909F'),0,'Test post #82','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 83,HEXTORAW('769A61B439DA4E69A8CABD5EB27AF112'),0,'Test post #83','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 84,HEXTORAW('C7A7547503AE4C29BA5C10D84B3FF33A'),0,'Test post #84','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 85,HEXTORAW('F5F5AF7F9FC249208DF5A92F33FD7505'),0,'Test post #85','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 86,HEXTORAW('8F19FBC463344DBFACE2BA1489DF41C7'),0,'Test post #86','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 87,HEXTORAW('59E19B063FB445EEB9124ED27CF84832'),0,'Test post #87','This is a test post created on 14/07/2020','{"likes":1797,"dislikes":503,"views":4483}' FROM dual UNION ALL 
	SELECT 88,HEXTORAW('027426987BAB4C98A52A6B5B28A95411'),0,'Test post #88','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 89,HEXTORAW('82C6AA299A914EA7B6407756E709F0C7'),0,'Test post #89','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 90,HEXTORAW('5593CF24B4AC444CB4D04710579E5078'),0,'Test post #90','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 91,HEXTORAW('805CDB1EA36B419DBC952616BA136356'),0,'Test post #91','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 92,HEXTORAW('1A69CA6E41F14CBAA8F8F6A128298064'),0,'Test post #92','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 93,HEXTORAW('3009D9354EE748C08B7A15646F66FE80'),0,'Test post #93','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 94,HEXTORAW('981EB865488B43379F480A841F9F5AAA'),0,'Test post #94','This is a test post created on 14/07/2020','{"likes":2449,"dislikes":3257,"views":8758}' FROM dual UNION ALL 
	SELECT 95,HEXTORAW('22732CDBA2374475BA014900EC90EA2C'),0,'Test post #95','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 96,HEXTORAW('D9E5202D897143BCA1FBD8C007F29DFA'),0,'Test post #96','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 97,HEXTORAW('8D978BE858CE4624BC238AD700E4A4E8'),0,'Test post #97','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 98,HEXTORAW('EB3A44FCA3B348EAA76F525D8B95B575'),0,'Test post #98','This is a test post created on 14/07/2020',NULL FROM dual UNION ALL 
	SELECT 99,HEXTORAW('424D0FF7C22344879E828543F8930D97'),0,'Test post #99','This is a test post created on 14/07/2020','{"likes":1138,"dislikes":3038,"views":4936}' FROM dual UNION ALL 
	SELECT 100,HEXTORAW('66DA63C2533643CE9AF789B904D1E00D'),0,'Test post #100','This is a test post created on 14/07/2020',NULL FROM dual;

COMMIT;

-------------------------------------------------------
-- Related sequences
-------------------------------------------------------

CREATE SEQUENCE "SEQ_POST" START WITH 1 MINVALUE 1 INCREMENT BY 10000 CACHE 100 NOCYCLE;

CALL RESET_SEQUENCES();

-------------------------------------------------------
