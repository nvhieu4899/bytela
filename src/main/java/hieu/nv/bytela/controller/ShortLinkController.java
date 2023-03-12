package hieu.nv.bytela.controller;

import hieu.nv.bytela.controller.request.StoreShortLinkRequest;
import hieu.nv.bytela.model.entity.shortlink.ShortLink;
import hieu.nv.bytela.service.ShortLinkService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("short-link")
public class ShortLinkController {

    ShortLinkService shortLinkService;

    public ShortLinkController(ShortLinkService shortLinkService) {
        this.shortLinkService = shortLinkService;
    }

    @PostMapping
    public ShortLink storeShortLink(@RequestBody StoreShortLinkRequest request) throws NoSuchAlgorithmException {
        if (request.shortLink() == null || request.shortLink().isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return shortLinkService.storeShortLink(request.shortLink());
    }

    @GetMapping("{shortLink}")
    public ShortLink getShortLink(@PathVariable(name = "shortLink", required = false) String shortLink)
    {
        if (shortLink == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<ShortLink> optionalShortLink = shortLinkService.getShortLink(shortLink);
        if (optionalShortLink.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optionalShortLink.get();
    }
    @PostMapping("customize")
    public ShortLink storeCustomizedShortLink(@RequestBody ShortLink request)
    {
        Optional<ShortLink> linkOptional = shortLinkService.storeCustomizedShortLink(request);
        if (linkOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return linkOptional.get();
    }
}
