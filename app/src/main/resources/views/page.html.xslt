<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:output method="html" indent="yes"/>

    <xsl:include href="util/identity.xslt"/>

    <xsl:param name="siteName" select="'Dataset'"/>
    <xsl:param name="baseUrl" select="''"/>
    <xsl:param name="path" select="'/'"/>
    <xsl:param name="query" select="''"/>

    <xsl:template match="/">
        <html>
            <head>
                <xsl:apply-templates select="." mode="head"/>
                <link rel="stylesheet" type="text/css" href="{$baseUrl}/bootstrap/css/bootstrap.css"/>
                <link rel="stylesheet" type="text/css" href="{$baseUrl}/bootstrap/js/bootstrap.js"/>
                <link rel="stylesheet" type="text/css" href="{$baseUrl}/css/styles.css"/>
            </head>
            <body>
                <xsl:apply-templates select="." mode="body"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="/page" mode="head">
        <title>
            <xsl:value-of select="concat(title, ' - ', $siteName)"/>
        </title>
    </xsl:template>

    <xsl:template match="/page" mode="body">
        <xsl:apply-templates select="navigation"/>
        <div class="container-fluid">
            <xsl:apply-templates select="content"/>
        </div>
    </xsl:template>

    <xsl:template match="navigation">
        <nav class="navbar navbar-expand-lg navbar-dark ds-bg-steel-blue">
            <div class="container">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"/>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <div class="navbar-nav">
                        <xsl:for-each select="item">
                            <a class="nav-link {if ($path = @href) then 'active' else ''}" aria-current="page"
                               href="{@href}">
                                <xsl:value-of select="."/>
                            </a>
                        </xsl:for-each>
                    </div>
                </div>
                <!--                <form class="d-flex" role="search" action="/search" method="get">-->
                <!--                    <input class="form-control me-0" type="search" placeholder="Search" aria-label="Search"-->
                <!--                           name="q" value="{$query}"/>-->
                <!--                </form>-->
            </div>
        </nav>
    </xsl:template>

</xsl:stylesheet>
