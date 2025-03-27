package com.biritum.pdf

internal fun Sequence<Byte>.decodeToString() = this.toList().toByteArray().decodeToString()

internal const val pdfWithOneEmptyPage = """%PDF-1.7
%¥±ë

1 0 obj
<< /Type /Pages
 /MediaBox [0 0 595 842]
 /Kids [3 0 R]
 /Count 1
>>
endobj

2 0 obj
<< /Length 0
>>
stream

endstream
endobj

3 0 obj
<< /Type /Page
 /Parent 1 0 R
 /Contents 2 0 R
 /Resources << /ProcSet [/PDF]
>>
>>
endobj

4 0 obj
<< /Type /Catalog
 /Pages 1 0 R
 /PageMode /UseNone
>>
endobj

xref
0 5
0000000000 65535 f 
0000000017 00000 n 
0000000102 00000 n 
0000000152 00000 n 
0000000252 00000 n 

trailer
<< /Root 4 0 R
 /Size 5
 /Info <</Producer(BiritumPdf)/Creator(BiritumPdf)>>
>>

startxref
323
%%EOF"""

internal const val pdfWithTwoPages = """%PDF-1.7
%¥±ë

1 0 obj
<< /Type /Pages
 /MediaBox [0 0 595 842]
 /Kids [3 0 R 5 0 R]
 /Count 2
>>
endobj

2 0 obj
<< /Length 82
>>
stream
0.0 0.0 0.0 RG 0.0 w 57 785 m 539 57 l S
0.0 0.0 0.0 RG 0.0 w 57 57 m 539 785 l S

endstream
endobj

3 0 obj
<< /Type /Page
 /Parent 1 0 R
 /Contents 2 0 R
 /Resources << /ProcSet [/PDF]
>>
 /Group << /Type /Group
 /S /Transparency
 /I true
 /CS /DeviceRGB
>>
>>
endobj

4 0 obj
<< /Length 82
>>
stream
0.0 0.0 0.0 RG 0.0 w 57 785 m 539 57 l S
0.0 0.0 0.0 RG 0.0 w 57 57 m 539 785 l S

endstream
endobj

5 0 obj
<< /Type /Page
 /Parent 1 0 R
 /Contents 4 0 R
 /Resources << /ProcSet [/PDF]
>>
 /Group << /Type /Group
 /S /Transparency
 /I true
 /CS /DeviceRGB
>>
>>
endobj

6 0 obj
<< /Type /Catalog
 /Pages 1 0 R
 /PageMode /UseOutlines
>>
endobj

xref
0 7
0000000000 65535 f 
0000000017 00000 n 
0000000108 00000 n 
0000000241 00000 n 
0000000411 00000 n 
0000000544 00000 n 
0000000714 00000 n 

trailer
<< /Root 6 0 R
 /Size 7
 /Info <</Producer(BiritumPdf)/Creator(BiritumPdf)>>
>>

startxref
789
%%EOF"""


internal const val pdfWithCross = """%PDF-1.7
%¥±ë

1 0 obj
<< /Type /Pages
 /MediaBox [0 0 595 842]
 /Kids [3 0 R]
 /Count 1
>>
endobj

2 0 obj
<< /Length 82
>>
stream
0.0 0.0 0.0 RG 0.0 w 57 785 m 538 57 l S
0.0 0.0 0.0 RG 0.0 w 57 57 m 538 785 l S

endstream
endobj

3 0 obj
<< /Type /Page
 /Parent 1 0 R
 /Contents 2 0 R
 /Resources << /ProcSet [/PDF]
>>
 /Group << /Type /Group
 /S /Transparency
 /I true
 /CS /DeviceRGB
>>
>>
endobj

4 0 obj
<< /Type /Catalog
 /Pages 1 0 R
 /PageMode /UseNone
>>
endobj

xref
0 5
0000000000 65535 f 
0000000017 00000 n 
0000000102 00000 n 
0000000235 00000 n 
0000000405 00000 n 

trailer
<< /Root 4 0 R
 /Size 5
 /Info <</Producer(BiritumPdf)/Creator(BiritumPdf)>>
>>

startxref
476
%%EOF"""


internal const val pdfWithBezier = """%PDF-1.7
%¥±ë

1 0 obj
<< /Type /Pages
 /MediaBox [0 0 595 842]
 /Kids [3 0 R]
 /Count 1
>>
endobj

2 0 obj
<< /Length 114
>>
stream
0.0 0.0 0.0 RG 0.0 w 57 785 m 298 421 298 421 538 785 c S
0.0 0.0 0.0 RG 0.0 w 57 57 m 298 421 298 421 538 57 c S

endstream
endobj

3 0 obj
<< /Type /Page
 /Parent 1 0 R
 /Contents 2 0 R
 /Resources << /ProcSet [/PDF]
>>
 /Group << /Type /Group
 /S /Transparency
 /I true
 /CS /DeviceRGB
>>
>>
endobj

4 0 obj
<< /Type /Catalog
 /Pages 1 0 R
 /PageMode /UseThumbs
>>
endobj

xref
0 5
0000000000 65535 f 
0000000017 00000 n 
0000000102 00000 n 
0000000268 00000 n 
0000000438 00000 n 

trailer
<< /Root 4 0 R
 /Size 5
 /Info <</Producer(BiritumPdf)/Creator(BiritumPdf)>>
>>

startxref
511
%%EOF"""