<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:csv="http://runnable.nl/dataset/csv"
                xmlns:content="http://runnable.nl/dataset/content">

    <xsl:output method="xml" indent="yes"/>

    <xsl:param name="pageOrientation" select="'portrait'"/>
    <xsl:param name="fontFamily" select="'Inter'"/>
    <xsl:param name="sortColumn" select="1"/>
    <xsl:param name="sortOrder" select="'ascending'"/>

    <xsl:include href="../util/identity.xslt"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait"
                                       page-width="210mm"
                                       page-height="297mm">
                    <fo:region-body margin-left="5mm" margin-right="5mm" margin-top="10mm" margin-bottom="10mm"/>
                    <fo:region-after region-name="xsl-region-after" extent="10mm"/>
                </fo:simple-page-master>
                <fo:simple-page-master master-name="A4-landscape"
                                       page-width="297mm"
                                       page-height="210mm">
                    <fo:region-body margin-left="10mm" margin-right="10mm" margin-top="15mm" margin-bottom="15mm"/>
                    <fo:region-after region-name="xsl-region-after" extent="10mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <xsl:apply-templates/>
        </fo:root>
    </xsl:template>

    <xsl:template match="csv:data">
        <fo:page-sequence master-reference="{concat('A4-', $pageOrientation)}">
            <fo:flow flow-name="xsl-region-body" font-family="{$fontFamily}">
                <fo:table table-layout="fixed" width="100%">
                    <xsl:apply-templates select="." mode="header"/>
                    <xsl:apply-templates select="." mode="body"/>
                </fo:table>
            </fo:flow>
        </fo:page-sequence>
    </xsl:template>

    <xsl:template match="csv:data" mode="header">
        <fo:table-header font-weight="bold">
            <fo:table-row>
                <xsl:for-each select="csv:row[1]/content:*">
                    <fo:table-cell>
                        <fo:block>
                            <xsl:value-of select="local-name(.)"/>
                        </fo:block>
                    </fo:table-cell>
                </xsl:for-each>
            </fo:table-row>
        </fo:table-header>
    </xsl:template>

    <xsl:template match="csv:data" mode="body">
        <fo:table-body>
            <xsl:for-each select="csv:row">
                <xsl:sort select="content:*[$sortColumn]" order="{$sortOrder}"/>
                <fo:table-row>
                    <xsl:for-each select="content:*">
                        <fo:table-cell>
                            <fo:block>
                                <xsl:value-of select="."/>
                            </fo:block>
                        </fo:table-cell>
                    </xsl:for-each>
                </fo:table-row>
            </xsl:for-each>
        </fo:table-body>
    </xsl:template>

</xsl:stylesheet>
