package com.whitrus.spring.tester.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class StringUtilsTest {

	@Test
	@DisplayName("String normalization is working as expected?")
	public void testStringNormalization() {

		String beforeConversion = "ÁÀÂÃÄÅàáâãäåĀāąĄÇçćĆčČďĎÈÉÊËèéêëěĚĒēęĘÌÍÎÏìíîïĪīłÑñňŇńŃÒÓÔÕÕÖòóôõöŌōřŘŠšśŚťŤÙÚÛÜùúûüůŮŪūŸÿýÝŽžżŻźŹ!#$%&'()*+,-./0123456789:;<=>?@[\\]^_`{|}~\"";
		String expectedResult = "AAAAAAAAAAAAAAAACCCCCCDDEEEEEEEEEEEEEEIIIIIIIIIILNNNNNNOOOOOOOOOOOOORRSSSSTTUUUUUUUUUUUUYYYYZZZZZZ";

		String afterConversion = StringUtils.normalize(beforeConversion);

		assertThat(afterConversion).isEqualTo(expectedResult);

		beforeConversion = """
				Não se preocupe em ter o brilho da Lua…
				Você já tem seu próprio valor…
				Deus te deu o dom da vida,
				aproveite este presente e brilhe…
				Não queira agradar a todos,
				isso será quase impossível…
				Mas trate as pessoas com amor, e procure falar a verdade,
				você pode não agradar a todos,
				mas terá o amor de muitos,
				principalmente das pessoas sinceras.
				Às vezes as pessoas não demonstram o quanto te querem bem,
				mas é que a gente nem sempre sabe expressar o que sente,
				temos até medo de demonstrar o que sentimos.
				Por isso, não se magoe atoa,
				aprenda a perdoar,
				você viverá bem mais leve,
				acima de tudo acredite:
				embora você muitas vezes se esqueça,
				você tem um grande valor pra Deus…
				E pra mim também…

				Eliezer Lemos""";

		expectedResult = "NAO SE PREOCUPE EM TER O BRILHO DA LUA VOCE JA TEM SEU PROPRIO VALOR DEUS TE DEU O DOM DA VIDA APROVEITE ESTE PRESENTE E BRILHE NAO QUEIRA AGRADAR A TODOS ISSO SERA QUASE IMPOSSIVEL MAS TRATE AS PESSOAS COM AMOR E PROCURE FALAR A VERDADE VOCE PODE NAO AGRADAR A TODOS MAS TERA O AMOR DE MUITOS PRINCIPALMENTE DAS PESSOAS SINCERAS AS VEZES AS PESSOAS NAO DEMONSTRAM O QUANTO TE QUEREM BEM MAS E QUE A GENTE NEM SEMPRE SABE EXPRESSAR O QUE SENTE TEMOS ATE MEDO DE DEMONSTRAR O QUE SENTIMOS POR ISSO NAO SE MAGOE ATOA APRENDA A PERDOAR VOCE VIVERA BEM MAIS LEVE ACIMA DE TUDO ACREDITE EMBORA VOCE MUITAS VEZES SE ESQUECA VOCE TEM UM GRANDE VALOR PRA DEUS E PRA MIM TAMBEM ELIEZER LEMOS";
		afterConversion = StringUtils.normalize(beforeConversion);

		assertThat(afterConversion).isEqualTo(expectedResult);

		beforeConversion = " João 		 marcos          D'allabrida höLZ!  ";
		expectedResult = "JOAO MARCOS DALLABRIDA HOLZ";
		afterConversion = StringUtils.normalize(beforeConversion);

		assertThat(afterConversion).isEqualTo(expectedResult);
	}
}
