/*
 * MIT License
 *
 * Copyright (c) 2019 Monbanquet.fr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package fr.monbanquet.sylph.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TodoBuilder {

    static String longText =
            "\n" +
                    "\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris risus nisi, rutrum non leo at, pharetra efficitur nunc. Curabitur at purus consectetur, finibus ipsum vulputate, dapibus ipsum. Ut ligula turpis, tempor non metus a, fringilla aliquam tortor. Nulla porta tellus in ligula gravida, a rhoncus ipsum porttitor. Nam ac diam dictum, volutpat enim ac, convallis felis. Pellentesque in posuere nisl, viverra malesuada turpis. Praesent dolor velit, aliquet vel tempor ut, suscipit at augue. Fusce iaculis metus non dolor gravida tincidunt. In ac ligula sit amet risus porta posuere. Cras et pharetra sem.\n" +
                    "\n" +
                    "Etiam facilisis, turpis sit amet placerat viverra, mi augue facilisis tellus, ac sagittis est nisi sit amet ante. Morbi sed dui sed nisi blandit fringilla. Pellentesque at ligula eu eros varius cursus sit amet in orci. Proin tempor eros lacus, ac consequat dui pretium et. Fusce sem velit, efficitur non est ac, congue consequat justo. Vivamus ultrices tempor ultrices. Nam tincidunt libero est, et tincidunt eros commodo sit amet. Phasellus odio ante, hendrerit sed bibendum ac, iaculis sit amet mauris. Cras ac odio faucibus, varius elit eget, tincidunt tellus. Aliquam ultrices leo magna, non cursus augue malesuada vitae. Ut et pulvinar ipsum.\n" +
                    "\n" +
                    "Aenean ultrices turpis sit amet leo tincidunt, vel tempus erat euismod. Integer vitae est id orci porttitor vehicula. Praesent pulvinar mollis enim vitae placerat. Proin imperdiet, tortor sed pretium ullamcorper, sapien lorem gravida sapien, sit amet tristique turpis ipsum a metus. Duis eu sollicitudin justo. Maecenas dignissim odio eget nisl lacinia hendrerit. Sed scelerisque quam nunc. Ut sit amet quam vitae ex tristique suscipit. Vivamus in massa varius, accumsan dui sed, efficitur magna.\n" +
                    "\n" +
                    "Aliquam erat volutpat. Etiam pulvinar lacinia sagittis. In euismod feugiat metus, vitae hendrerit risus imperdiet at. Praesent at ligula facilisis, euismod justo quis, tincidunt ipsum. Suspendisse at quam eget erat mattis eleifend. Phasellus eget neque suscipit, vestibulum nunc sit amet, tempor ex. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla a neque rhoncus, tempor velit eu, finibus est. Vivamus porttitor elit et semper egestas. Praesent non consectetur velit. Curabitur ornare dolor et blandit pulvinar. Praesent egestas magna ut risus suscipit, non elementum sem malesuada. Etiam elit velit, dictum eget justo sed, cursus accumsan ipsum. Fusce tristique varius arcu nec volutpat. Morbi pharetra metus eget porttitor pellentesque.\n" +
                    "\n" +
                    "Fusce rutrum nulla vitae mauris malesuada, nec ullamcorper ex gravida. Pellentesque luctus ullamcorper erat eu ultrices. Aliquam risus est, accumsan ut tortor sed, sodales tempor nulla. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque id elementum augue. Suspendisse facilisis magna vel eleifend imperdiet. Quisque nec vulputate mi, sit amet aliquam nisl. Suspendisse potenti. Sed varius feugiat neque.\n" +
                    "\n" +
                    "Duis sed maximus felis, eu tristique sapien. Fusce euismod mi mauris, at aliquet lorem pretium sed. Proin mollis purus id neque hendrerit, nec laoreet lacus euismod. Vivamus molestie tellus odio, et mollis lacus egestas et. Sed accumsan rhoncus vestibulum. Vivamus rhoncus eros quis sodales venenatis. In fermentum, dui vitae porttitor porta, tortor nulla efficitur nulla, sed fringilla dui nunc ullamcorper ipsum. Sed mattis accumsan sapien sit amet dictum. Aenean at malesuada odio, a dictum metus. Integer vel mauris ut justo luctus ultrices nec nec mi. Sed venenatis neque eget tellus luctus, sed consequat odio facilisis. Proin sit amet faucibus nisl, ac pretium ante.\n" +
                    "\n" +
                    "Nunc interdum hendrerit urna, eget eleifend orci sollicitudin sed. Maecenas sed finibus ex. Morbi vitae malesuada turpis. Ut tellus nunc, dignissim a ultrices eget, imperdiet vitae risus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Phasellus gravida, libero sed consectetur commodo, libero elit fermentum metus, tincidunt fringilla sem magna at magna. Suspendisse dapibus metus eu quam ornare mattis.\n" +
                    "\n" +
                    "Morbi convallis, urna vel pretium sodales, ligula lorem pellentesque felis, vitae sollicitudin diam sem quis neque. Phasellus porttitor tincidunt accumsan. Pellentesque id diam pretium, euismod metus ac, molestie sapien. Aenean bibendum tincidunt congue. Duis gravida justo ut mauris finibus scelerisque. Aenean lobortis efficitur eros, eu finibus urna pretium et. Interdum et malesuada fames ac ante ipsum primis in faucibus. Cras id rutrum velit. Proin ut laoreet ex.\n" +
                    "\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vel congue nibh. Nulla sed faucibus nunc, sed maximus urna. Nunc ac purus molestie arcu fermentum condimentum vitae nec nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris et placerat nisi. Etiam orci turpis, porta in mattis in, lobortis sit amet sapien. Quisque tristique augue neque, quis rutrum lacus porta dictum. Aenean dictum dolor eget convallis pharetra. Donec commodo ante et risus accumsan tincidunt. Morbi nisl mi, molestie in mollis sit amet, sodales nec mauris. Pellentesque vel vehicula diam. Cras ornare tortor eu fringilla tincidunt. Cras lacinia lacus dolor, a maximus nulla faucibus non. Vestibulum porttitor eros felis, quis malesuada orci euismod sollicitudin. In ac ex bibendum mi accumsan suscipit quis sed massa.\n" +
                    "\n" +
                    "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc finibus purus vitae ante laoreet, iaculis dignissim nisl auctor. Sed sed metus nisl. Nunc et aliquam justo. Nulla vitae scelerisque nisi. Ut nisi ex, ultricies quis tempor in, sodales eget nisi. Etiam quis arcu et ex viverra egestas eget eget diam. Proin dignissim at lectus ultricies faucibus. Integer eget velit sed felis vulputate hendrerit.\n" +
                    "\n" +
                    "Morbi dictum dui ut erat accumsan iaculis. Suspendisse luctus, augue nec porta varius, ex tellus fringilla felis, vitae sagittis nulla est a mauris. Quisque aliquam sem sed libero porttitor bibendum. Etiam fringilla malesuada pharetra. Donec in ex ante. Fusce id suscipit sapien, nec euismod dolor. Quisque rhoncus auctor nisi, eget ornare velit volutpat nec. Donec cursus scelerisque lectus, et congue risus consequat non.\n" +
                    "\n" +
                    "Duis nec maximus purus. Vivamus pellentesque ultrices accumsan. Donec placerat, neque vel feugiat mattis, nunc sem bibendum lorem, ut vehicula leo elit in orci. Etiam ornare, sem ut imperdiet fermentum, nibh ipsum lobortis mi, nec vehicula ipsum erat sit amet est. Pellentesque pellentesque purus eget ex finibus, rhoncus feugiat sem sollicitudin. Integer congue augue id nisi rutrum, vitae faucibus sapien venenatis. Aenean sit amet augue nunc.\n" +
                    "\n" +
                    "Nunc ante ex, finibus ut efficitur a, vulputate ut sem. Cras lacus purus, scelerisque ut ullamcorper tempor, luctus et nisi. Fusce laoreet libero turpis, at viverra nunc viverra sit amet. Fusce aliquet sapien orci, nec consequat metus mattis id. Maecenas non metus ante. Proin scelerisque eget nibh sed cursus. Praesent egestas elementum risus in finibus. Curabitur semper malesuada imperdiet. Pellentesque nec cursus mi, quis consequat lectus. Integer vel pulvinar augue. Vivamus a sem rhoncus, pellentesque tortor sit amet, volutpat purus. Duis auctor risus nec ipsum iaculis posuere. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas pretium, felis non tincidunt porta, arcu risus sodales justo, a pharetra metus eros eu purus.\n" +
                    "\n" +
                    "Integer varius scelerisque elit vitae ultricies. Etiam ut dignissim ex. Fusce eget euismod est, at dapibus mauris. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam erat felis, pellentesque eget diam nec, egestas porttitor mi. Donec volutpat, nisi vel varius bibendum, nisi ex interdum ligula, a tempor orci mauris vel tellus. Donec pellentesque erat quis tortor facilisis interdum. Vivamus elementum vel tortor vitae venenatis. Sed vitae mollis neque. Ut pulvinar nisl id quam iaculis finibus. Nulla facilisi. Morbi sit amet faucibus tellus. Suspendisse vulputate eu enim nec pharetra. In dapibus arcu quis tempor imperdiet.\n" +
                    "\n" +
                    "Curabitur consequat, tortor quis pulvinar gravida, nunc orci iaculis nunc, ultricies suscipit arcu ligula a sapien. Integer dictum id nisl ac tincidunt. Aliquam ut pellentesque metus. Nam condimentum lorem eget lorem malesuada placerat. Etiam vestibulum arcu at urna hendrerit, sit amet eleifend est congue. Praesent lobortis tortor libero, quis gravida diam fermentum ut. Vivamus semper, felis ac gravida ultricies, odio dui facilisis urna, eu luctus sem lectus nec sapien.\n" +
                    "\n" +
                    "Aenean bibendum est quis dui euismod sollicitudin. Duis finibus, purus sit amet dictum egestas, lacus elit pellentesque lacus, at dictum est velit et massa. Curabitur quam lectus, tempus sit amet porttitor quis, egestas non dolor. In iaculis nibh venenatis lacinia commodo. Suspendisse vitae congue ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vivamus placerat ipsum nunc, at ultrices diam sodales eget. Pellentesque consectetur nulla quam, nec efficitur risus sagittis ut.\n" +
                    "\n" +
                    "Pellentesque a sapien at magna interdum mollis at ut eros. Integer pharetra mauris eget commodo convallis. Morbi semper nec sapien et molestie. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent nec ornare neque. Praesent ac tellus lacus. Phasellus vel faucibus sem, eget maximus lorem. Mauris sodales ante et dolor eleifend sollicitudin. Fusce at elit nulla. Maecenas consequat hendrerit est et laoreet. In ornare, dui vel semper venenatis, arcu nibh euismod ante, commodo consequat quam felis quis nulla.\n" +
                    "\n" +
                    "Fusce non justo sed velit lacinia tempor vitae vitae tortor. Vivamus id varius nunc. Morbi sed rutrum enim. Donec facilisis risus id luctus suscipit. Duis at scelerisque urna. Vestibulum et felis dapibus, dapibus lacus tempor, posuere augue. Sed semper rutrum metus, ac dictum sem dictum et. Integer consequat leo ac eros rhoncus sagittis. Aliquam condimentum, velit eu imperdiet scelerisque, ex est aliquam lectus, ut sagittis quam arcu non turpis. Morbi sit amet vulputate diam. Donec vel varius sapien, eu ullamcorper magna. Praesent nec lacus sit amet felis elementum tincidunt.\n" +
                    "\n" +
                    "Proin at velit nisi. In vitae tempus neque. Integer maximus tortor sodales elit tempor, quis auctor augue efficitur. Mauris sodales eu est quis faucibus. Morbi ut nunc sit amet nunc facilisis sagittis. Praesent ipsum lorem, suscipit accumsan eros a, gravida pretium elit. Suspendisse eget ante vitae elit iaculis accumsan nec a augue. In eget diam dignissim, porta mi et, ultrices turpis. Suspendisse auctor mauris eu luctus scelerisque.\n" +
                    "\n" +
                    "Fusce sem libero, cursus id erat eu, congue accumsan neque. Integer dapibus erat a ornare pharetra. In vestibulum tellus sit amet semper eleifend. Sed consequat, erat condimentum accumsan ultrices, dui ligula aliquet velit, a ornare arcu urna quis urna. Ut viverra pretium facilisis. Morbi eget nunc ut nisi vehicula imperdiet quis nec ex. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nunc vitae erat tellus. Aliquam elementum odio vel convallis scelerisque. Vivamus hendrerit orci mi, vitae fringilla nisi luctus eget.\n" +
                    "\n" +
                    "Morbi cursus sed nibh in tempus. In quis lacus ornare, rhoncus ante sit amet, malesuada justo. Vivamus vel dictum ligula, a sagittis quam. Aenean vehicula risus vitae tellus congue ullamcorper. Mauris a justo molestie, ullamcorper nunc quis, gravida libero. Aenean quis placerat quam. Donec urna mi, tincidunt id finibus sed, placerat non odio. Sed at risus non elit facilisis ullamcorper sit amet sed sem. Donec nec nisi nibh. Vestibulum faucibus bibendum eros nec imperdiet.\n" +
                    "\n" +
                    "Donec ut bibendum ante, vitae feugiat enim. Sed massa nisi, porta vel dolor ut, posuere aliquet sapien. Proin vitae urna et dui rutrum ultricies sed non nunc. Suspendisse dignissim eros a urna ornare molestie. Nulla sodales rutrum quam id malesuada. Nunc posuere at ipsum in commodo. Donec vitae pellentesque justo. Morbi id egestas orci. Nullam urna felis, volutpat sit amet turpis eu, faucibus vulputate augue. Ut et neque convallis, auctor ante quis, efficitur mauris. Ut a luctus libero, aliquam feugiat neque. Mauris id pharetra mi, in suscipit ante.\n" +
                    "\n" +
                    "Aliquam eget nisi condimentum, vestibulum felis at, luctus ligula. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed vestibulum vel justo ut fermentum. Curabitur dignissim vitae erat a ultricies. Nullam euismod ornare mi non fermentum. Ut cursus lacus at metus consectetur, vitae feugiat libero ultricies. Fusce accumsan urna pulvinar eros placerat, nec dignissim nisi dignissim. Integer vitae turpis ante.\n" +
                    "\n" +
                    "Donec augue nisi, auctor a maximus pellentesque, rutrum ut nisl. Aenean luctus, nisl non tincidunt bibendum, est sem tristique odio, bibendum sollicitudin mi lorem vel velit. Phasellus ut vestibulum arcu. Maecenas fringilla, lectus vel euismod aliquet, ipsum sem luctus leo, nec bibendum lorem justo sed ipsum. Nullam in velit at orci laoreet mattis. Mauris placerat nisl eu quam lobortis, non accumsan velit finibus. Proin posuere et erat a gravida. Aliquam sit amet urna posuere, feugiat lectus tempus, hendrerit arcu. Maecenas et viverra augue. Etiam nunc mauris, ullamcorper interdum libero a, congue finibus mi.\n" +
                    "\n" +
                    "Phasellus accumsan magna odio, vel molestie elit viverra nec. In rutrum ligula vel turpis scelerisque, eget porttitor quam volutpat. Aliquam facilisis ante urna, at mattis ex tincidunt et. Fusce gravida quis odio eu volutpat. Donec egestas vitae nisl at luctus. Vestibulum faucibus aliquam maximus. Proin gravida, dui sit amet porta sollicitudin, diam eros imperdiet nunc, hendrerit vestibulum tortor ipsum at lectus. Vivamus a porttitor velit. Suspendisse sit amet arcu et dolor varius vehicula. Duis ut efficitur magna.\n" +
                    "\n" +
                    "Praesent justo lectus, consequat sed venenatis eget, lobortis nec augue. Praesent vel ullamcorper leo. Sed ac imperdiet odio. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tortor metus, fermentum ut neque sit amet, efficitur posuere nisi. Integer eu arcu dui. Sed nec elit in erat dignissim dignissim a vel arcu. Sed id risus eu nulla placerat congue. Vivamus ultrices dolor sit amet condimentum pulvinar. Cras commodo nunc eget libero aliquet pharetra. Curabitur convallis semper magna vel elementum.\n" +
                    "\n" +
                    "Nam dapibus, purus sed convallis fermentum, turpis augue finibus sapien, et tempor enim nunc eget eros. Vestibulum mollis porta est in mattis. Sed tellus neque, ultricies at mi vehicula, posuere suscipit mauris. Fusce venenatis cursus nisl quis tristique. Aliquam nunc leo, feugiat eget libero eu, sodales vulputate mauris. Donec libero justo, lacinia nec ultrices eget, facilisis ut lacus. Suspendisse ultrices magna vitae nisl placerat, in volutpat sapien lobortis. Donec dictum orci est, at facilisis nunc ornare vel. Etiam tincidunt convallis purus et blandit. Vestibulum cursus eget ligula et tincidunt. Vestibulum eget posuere sapien, in ultrices purus. Nunc consectetur pulvinar placerat. Sed blandit porttitor laoreet.\n" +
                    "\n" +
                    "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Aliquam mollis mollis pulvinar. Ut sodales enim nisi, ac auctor libero egestas sed. Praesent sodales non ex non vestibulum. Sed porta nisi vitae lectus facilisis, eget aliquam dolor porttitor. Praesent pharetra, ipsum eget vehicula scelerisque, libero nunc pharetra massa, a ornare nunc lectus eu ipsum. Pellentesque diam mi, facilisis sit amet ex vehicula, volutpat pellentesque turpis. Duis tincidunt felis faucibus vehicula dapibus. Phasellus sed dolor arcu. Nulla imperdiet diam at odio eleifend, at viverra orci imperdiet. Etiam condimentum augue tincidunt erat tempus posuere. Curabitur ultricies ornare nibh, eget maximus nisl venenatis quis. Cras non malesuada velit. Vivamus ullamcorper elit eu nunc imperdiet, eget molestie enim fermentum. Duis egestas mattis nibh sed bibendum. Quisque mauris tellus, pulvinar non consectetur sit amet, congue ac leo.\n" +
                    "\n" +
                    "Aenean quis felis fermentum, tristique turpis ac, condimentum metus. Nulla facilisi. Sed vel arcu elementum leo fermentum placerat at eget dolor. Nulla sem enim, hendrerit eget iaculis a, condimentum vel nibh. Nulla imperdiet facilisis accumsan. Duis mi velit, fermentum at consectetur et, auctor nec mi. Praesent eget varius lectus, sit amet consectetur justo. Proin consequat pretium nisi eu finibus. Curabitur ut tincidunt felis, eu porta metus. Duis nec fringilla urna. Mauris ornare urna purus, nec mollis erat suscipit eget. Morbi non eleifend leo. Cras viverra mollis augue in accumsan. Nullam rutrum orci non augue viverra, ut porta dolor efficitur. Praesent in nisi quis nunc congue placerat eu quis sem. Cras fermentum laoreet mi vitae interdum.\n" +
                    "\n" +
                    "Vivamus vehicula ornare risus, dictum luctus mi vulputate eu. Curabitur cursus arcu a risus tristique tincidunt. Suspendisse magna quam, porttitor vel elementum vel, tristique sed leo. Vestibulum volutpat auctor erat, eu interdum lacus aliquam vel. Fusce felis felis, porta convallis lorem ac, egestas commodo eros. Sed lacus nulla, porta sit amet gravida quis, auctor sit amet leo. Vestibulum in ipsum vitae turpis lobortis sollicitudin. Suspendisse eleifend gravida elit eu efficitur. Ut a consequat quam. Curabitur sollicitudin massa nec metus mollis congue. Vestibulum enim justo, pellentesque sed nulla sed, sodales pellentesque dolor. Ut aliquam nec lectus id dictum. Cras vehicula, libero a dapibus faucibus, nisl purus bibendum odio, quis consectetur ligula odio id quam. Aenean vel justo magna.\n" +
                    "\n" +
                    "Phasellus maximus, ligula eget varius bibendum, libero risus viverra felis, ac rutrum risus eros sit amet ipsum. Vivamus accumsan nisl a urna fermentum, a lobortis augue blandit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis quis augue vestibulum, lobortis quam in, tempor sem. Integer porttitor aliquam dui, quis sollicitudin tellus semper viverra. Morbi ac luctus diam. Vestibulum vitae hendrerit libero. Sed rutrum vitae metus non ullamcorper. Duis dignissim nisl in eleifend eleifend. Aenean varius lobortis efficitur. Donec tortor sapien, finibus at luctus non, porttitor in tortor. Quisque facilisis ac lectus quis tempor. Suspendisse eget bibendum est.\n" +
                    "\n" +
                    "Praesent odio tellus, hendrerit eu nisi non, pharetra consectetur lorem. Cras dapibus orci ac erat sodales, et rutrum orci vulputate. Proin vel libero quis justo dictum fringilla a sit amet lacus. Pellentesque volutpat convallis mi sed sollicitudin. Vivamus imperdiet velit consequat felis semper cursus. In pellentesque ut lectus ut imperdiet. Etiam venenatis feugiat diam, non dictum magna. In hac habitasse platea dictumst. Nulla vel rutrum nibh. Curabitur laoreet at dui quis consequat. Nunc egestas, eros at placerat bibendum, felis ligula varius velit, at mollis libero orci vitae tortor. Etiam in consectetur ex. Donec mattis feugiat facilisis. Suspendisse potenti.\n" +
                    "\n" +
                    "Quisque tincidunt sodales ex, quis dapibus purus blandit ut. Nam nec faucibus orci. Aenean faucibus orci dui, id volutpat enim posuere a. Duis tincidunt, mauris at gravida imperdiet, mi velit eleifend felis, nec dictum ipsum quam at nulla. Integer accumsan varius dignissim. Curabitur risus augue, sagittis quis sagittis scelerisque, porta in nulla. Vivamus et nunc in mauris molestie imperdiet. Nam vestibulum porttitor neque at bibendum. Ut accumsan felis aliquam ex convallis, quis elementum nisl elementum. Proin id varius velit, ut commodo ex. Praesent at massa nibh. Curabitur sit amet purus vitae quam faucibus mattis. Suspendisse porta blandit ante non iaculis. Aliquam viverra nec metus in luctus.\n" +
                    "\n" +
                    "Aenean vehicula neque sed nulla facilisis, id facilisis ex vehicula. Mauris purus eros, blandit a lectus efficitur, tincidunt fermentum tortor. Morbi volutpat aliquet nulla, in imperdiet mi. Duis egestas dignissim lectus, in porttitor risus varius sed. Ut vitae lacus erat. Morbi posuere ante sed nibh fringilla, eu aliquet libero maximus. Fusce faucibus suscipit felis a euismod. Nunc consequat varius quam quis luctus. Ut at orci vitae felis lobortis convallis et eu felis. In hac habitasse platea dictumst. Pellentesque dapibus tempus felis blandit semper.\n" +
                    "\n" +
                    "Phasellus lacinia tortor id odio ullamcorper, sit amet aliquet nisl faucibus. Suspendisse accumsan risus eu porta faucibus. Nunc eu nisl a augue semper elementum. Nam tempor scelerisque pretium. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce in sapien et ex vulputate elementum. In purus ante, dignissim bibendum magna sed, accumsan imperdiet turpis. Morbi sit amet vestibulum justo, id semper sem. In hac habitasse platea dictumst. Phasellus mi eros, sodales ac eros at, blandit lobortis libero. Nullam a condimentum lorem. Cras in rutrum elit. Ut finibus ullamcorper lacus id dignissim. Ut placerat hendrerit lectus, ut mollis est porta sed. Mauris vulputate egestas ipsum, vel aliquam risus dapibus at.\n" +
                    "\n" +
                    "Proin eu convallis dolor, id euismod metus. Curabitur id mauris ultrices, vulputate dui vitae, pretium justo. Nullam feugiat neque congue, tristique mauris in, gravida metus. Sed id rutrum arcu. Cras pellentesque a neque at bibendum. Morbi a justo gravida, varius nibh nec, suscipit nisi. Quisque sapien eros, iaculis vel ipsum ac, euismod aliquam nibh.\n" +
                    "\n" +
                    "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum hendrerit nulla ex, a efficitur ligula auctor vestibulum. Cras id orci turpis. Maecenas viverra orci quis mi vulputate feugiat. Nam nec mi eu leo sodales euismod. Mauris nec faucibus mi. Vestibulum id cursus sem. Etiam nec pellentesque elit. Fusce non felis libero. In eget est tellus. Integer libero est, imperdiet ut sodales at, vulputate a nulla. Nunc leo orci, efficitur vel semper a, elementum sit amet justo. Pellentesque eu sem eget est varius commodo.\n" +
                    "\n" +
                    "Nullam fringilla ligula ac purus congue, at hendrerit lectus maximus. Donec sed lacus volutpat dolor malesuada cursus et in eros. Nam a dictum mauris. Donec eget consequat ipsum. Phasellus luctus nibh facilisis, consectetur augue vel, blandit lectus. Proin tristique convallis facilisis. Praesent sed elit turpis. In eu dictum urna. Nulla eu dui ac elit pulvinar scelerisque id a enim. Mauris ut leo molestie lectus faucibus volutpat.\n" +
                    "\n" +
                    "Phasellus interdum posuere lacus, quis congue tellus. Donec finibus, tellus sed porta molestie, erat felis gravida justo, vitae elementum turpis tortor sit amet nibh. Integer ultricies suscipit tellus, in bibendum odio pharetra non. Integer sed porta purus, interdum varius turpis. Mauris feugiat sollicitudin odio, vel aliquet neque lobortis sed. Donec ornare augue ac aliquet consequat. Aenean nunc lorem, viverra vitae metus et, accumsan semper lectus. Donec interdum scelerisque velit eu dapibus. Etiam a nulla lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec ornare nec lacus eu tincidunt.\n" +
                    "\n" +
                    "Suspendisse auctor pulvinar ipsum, vel lacinia elit bibendum sagittis. Donec semper sed odio id efficitur. Duis ullamcorper nec lacus et porta. Curabitur ex leo, placerat non gravida eget, viverra a leo. Etiam venenatis laoreet orci, sed tincidunt lorem efficitur id. Proin vitae risus in mi commodo interdum id ut diam. Maecenas aliquet mollis aliquam. Praesent malesuada sem a massa tincidunt, ornare iaculis massa viverra. Suspendisse potenti. Nunc nec sapien et nulla auctor lacinia.\n" +
                    "\n" +
                    "Nunc eros sem, varius vel felis ac, ornare rhoncus sapien. Nunc interdum eleifend tincidunt. Praesent vel mauris volutpat, ullamcorper nibh sed, molestie felis. Nam commodo est nec diam finibus bibendum. Donec quis orci ut arcu lacinia finibus sit amet porta lectus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In convallis ullamcorper quam vel vulputate. In hac habitasse platea dictumst. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam a justo porttitor dolor suscipit aliquet. Morbi a feugiat leo. Sed in eros quis nunc laoreet dignissim ac ut nibh. Quisque dignissim metus magna, a venenatis eros varius eu. Suspendisse potenti. Ut mollis varius dui ac ultrices.\n" +
                    "\n" +
                    "Suspendisse potenti. Aenean sagittis, ipsum ut pulvinar consectetur, libero eros aliquam eros, nec dictum orci felis sit amet justo. Sed ut urna id libero sodales mattis. Donec vel velit facilisis, dignissim odio quis, maximus mauris. Nullam pretium purus sit amet lorem accumsan, vel aliquet justo sodales. Maecenas et libero eu mauris tincidunt porttitor ac nec quam. Vivamus vel mi neque. Pellentesque nisi urna, tempor a mollis sed, facilisis a lorem. Donec blandit magna eros, nec lacinia lectus mollis et. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nam dignissim maximus dictum. Maecenas viverra interdum tempor. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec hendrerit ex nisl, a rutrum justo elementum in.\n" +
                    "\n" +
                    "Fusce dignissim odio leo, sit amet ullamcorper velit dapibus quis. Sed placerat sit amet turpis scelerisque feugiat. Nunc venenatis vestibulum tincidunt. Aenean convallis, odio quis porta laoreet, massa elit semper lacus, eu auctor dolor ligula sed lorem. Cras sodales dapibus tellus, maximus rhoncus libero ultricies eu. Nullam et tincidunt sapien. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet sem justo. Suspendisse pulvinar lorem nisi, ut consectetur elit tempor sit amet. Praesent dictum ipsum vel accumsan eleifend.\n" +
                    "\n" +
                    "Donec ultrices eu lacus eu auctor. Fusce porta vehicula urna in fermentum. Pellentesque viverra laoreet facilisis. Phasellus congue gravida ex sed molestie. Sed consectetur consequat ante vel semper. Donec pretium lectus sed elit interdum venenatis. Vestibulum commodo tempor pellentesque. Nam maximus hendrerit bibendum. Pellentesque volutpat mauris nisl, et lobortis enim ullamcorper quis. In mattis dolor vel urna elementum, vel iaculis dui rutrum. Pellentesque at lectus at quam convallis egestas quis vel purus. Ut fringilla eget magna eget cursus. Mauris blandit maximus porta. Suspendisse varius sapien vel dui posuere eleifend.\n" +
                    "\n" +
                    "Mauris pretium nibh at pretium convallis. Vivamus fermentum urna leo. Mauris tempus massa ex, quis fermentum diam egestas nec. Etiam dolor mauris, congue sagittis tincidunt nec, ultrices vel dolor. Morbi vel sapien id ex volutpat bibendum. Praesent massa purus, sodales vitae arcu a, eleifend pellentesque eros. Cras nec enim in lorem fringilla laoreet. Vestibulum sed aliquam sapien. Aliquam at lacus ligula. Praesent fringilla erat sit amet posuere egestas. Aliquam luctus laoreet dolor nec vestibulum. Nunc mi tellus, porttitor ac volutpat ut, ullamcorper in augue. Sed neque magna, blandit a arcu et, vehicula congue arcu. Sed pretium pellentesque nisl, non accumsan nisi gravida at.\n" +
                    "\n" +
                    "Cras nec leo non enim volutpat laoreet sit amet et metus. Nulla ultrices ligula ut elit mattis, quis accumsan turpis scelerisque. Aliquam varius placerat enim. Nullam gravida aliquet scelerisque. Morbi non nulla a tellus volutpat dictum at nec mauris. In non gravida tortor. Fusce placerat erat id ex elementum, sit amet faucibus eros accumsan. Nullam vel nisi at enim ultrices fermentum. Nam gravida velit interdum, ultricies risus vitae, ultrices lorem. Phasellus lectus elit, posuere non mauris convallis, semper maximus leo. Nunc eget hendrerit purus, vitae tincidunt ipsum. Sed id lorem eros. Suspendisse potenti. Donec eu elit ornare nunc rutrum bibendum. Maecenas quis dapibus dui.\n" +
                    "\n" +
                    "Curabitur posuere, justo ut facilisis euismod, nisi elit sollicitudin risus, faucibus luctus justo mauris nec ipsum. Fusce sagittis euismod mattis. Fusce pretium laoreet nulla, tristique aliquam lacus sollicitudin et. Morbi porta erat nec laoreet suscipit. Duis urna nibh, elementum ut ex nec, tempor tincidunt mi. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam congue iaculis dolor sit amet condimentum. Integer sed dictum magna, vel euismod velit. Maecenas ut ante ipsum. Donec et orci at purus auctor lacinia a ut tellus. Quisque et imperdiet mauris, et semper felis. Maecenas eu neque id dui auctor sagittis a vitae tortor. Maecenas auctor nibh et ligula fringilla, sit amet malesuada orci elementum. Sed semper commodo bibendum. Donec commodo tellus sit amet ipsum suscipit, nec aliquam sem auctor. Maecenas dignissim, ante nec mollis fermentum, nibh erat pulvinar urna, sit amet faucibus tortor orci vel felis.\n" +
                    "\n" +
                    "Nulla a dui ac augue ultrices ornare at sed est. Sed sed molestie ipsum. Phasellus aliquet ac velit sagittis maximus. Integer lobortis placerat diam, sit amet fermentum turpis vulputate ac. Fusce enim ante, consectetur at malesuada elementum, pellentesque non diam. Quisque sit amet mauris eu dolor volutpat semper at at erat. Mauris pellentesque nisl vel libero tincidunt mollis. Fusce et placerat justo. Sed suscipit ultrices iaculis. Maecenas mi urna, dapibus quis auctor et, dapibus vitae nibh. Donec enim nibh, consectetur a varius non, ullamcorper commodo quam.\n" +
                    "\n" +
                    "Fusce nec odio et mi venenatis rutrum ac non lacus. Mauris vel pulvinar lacus. Duis dignissim lacus libero, ac consectetur ligula sollicitudin non. In eu dolor eget elit auctor dictum. Phasellus nec dolor ipsum. Quisque nunc ipsum, auctor eu massa id, iaculis viverra neque. Curabitur maximus nulla quis urna hendrerit, id commodo odio finibus.\n" +
                    "\n" +
                    "Suspendisse sed laoreet nulla. Vivamus ultricies volutpat lacus, in iaculis orci suscipit a. Pellentesque nec scelerisque felis. Integer venenatis sem in pulvinar vulputate. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Suspendisse at ligula tempor, semper felis ut, venenatis lectus. Vivamus convallis dictum risus tristique fermentum. Pellentesque vitae turpis urna. Phasellus a euismod ipsum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce quis nunc nec risus pharetra hendrerit. Sed accumsan lacus ac velit condimentum, vitae blandit ante lacinia. Aliquam nec lobortis erat. ";

    public static Todo newTodo() {
        return newTodo(44);
    }

    public static Todo newTodo(int id) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setUserId(5);
        todo.setTitle("A title");
        todo.setCompleted(true);
        return todo;
    }

    public static TodoExtended newTodoExtended() {
        return newTodoExtended(44);
    }

    public static TodoExtended newTodoExtended(int id) {
        TodoExtended todo = new TodoExtended();
        todo.setId(id);
        todo.setUserId(5);
        todo.setTitle(longText);
        todo.setCompleted(true);
        todo.setA(71d);
        todo.setB(longText);
        todo.setC(444444444444l);
        todo.setD(List.of("a", "b", "c", "dd", "eee", "ffff", "ggggg"));
        todo.setE(Map.of(
                "a", newTodo(),
                "bb", newTodo(),
                "ccc", newTodo(),
                "dddd", newTodo(),
                "eeeee", newTodo()
        ));
        todo.setF(IntStream.range(1, 10).mapToObj(i -> newTodo()).collect(Collectors.toList()));
        todo.setG(LocalDateTime.now());
        todo.setH(LocalDate.now());
        todo.setI(LocalTime.now());
        return todo;
    }

}
