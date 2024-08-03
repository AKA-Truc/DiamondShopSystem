package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Diamond;
import goldiounes.com.vn.models.DiamondDetail;
import goldiounes.com.vn.services.DiamondDetailSevice;
import goldiounes.com.vn.services.DiamondSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diamond-management")
public class DiamondController {

    @Autowired
    private DiamondSevice diamondService;

    @Autowired
    private DiamondDetailSevice diamondDetailService;

    @PostMapping("/diamonds")
    public Diamond createDiamond(@RequestBody Diamond diamond) {
        Diamond existingDiamond = diamondService.findDiamond(diamond.getCarat(),diamond.getClarity(),diamond.getColor(),diamond.getCut(),diamond.getOrigin());
        if (existingDiamond != null) {
            throw new RuntimeException("Diamond already exists");
        }
        return diamondService.createDiamond(diamond);
    }

    @GetMapping("/diamonds")
    public List<Diamond> getAllDiamonds() {
        List<Diamond> diamonds = diamondService.findAll();
        if (diamonds.isEmpty()) {
            throw new RuntimeException("No diamonds found");
        }
        return diamonds;
    }

    @GetMapping("/diamonds/{id}")
    public Diamond getDiamond(@PathVariable int id) {
        Diamond existingDiamond = diamondService.findById(id);
        if (existingDiamond == null) {
            throw new RuntimeException("Diamond not found");
        }
        return existingDiamond;
    }

    @PutMapping("/diamonds/{id}")
    public void updateDiamond(@PathVariable int id, @RequestBody Diamond diamond) {
        diamondService.updateDiamond(id, diamond);
    }

    @DeleteMapping("/diamonds/{id}")
    public void deleteDiamond(@PathVariable int id) {
        diamondService.deleteDiamond(id);
    }

    @PostMapping("/diamonds/{diamondId}/details")
    public DiamondDetail createDiamondDetail(@PathVariable int diamondId, @RequestBody DiamondDetail diamondDetail) {
        diamondDetail.setDiamond(diamondService.findById(diamondId));
        return diamondDetailService.save(diamondDetail);
    }

    @GetMapping("/diamonds/{diamondId}/details")
    public List<DiamondDetail> getDiamondDetailsByDiamondId(@PathVariable int diamondId) {
        return diamondDetailService.findByDiamondId(diamondId);
    }

    @DeleteMapping("/diamonds/{diamondId}/details/{id}")
    public void deleteDiamondDetail(@PathVariable int diamondId, @PathVariable int id) {
        DiamondDetail existingDiamondDetail = diamondDetailService.findById(id);
        if (existingDiamondDetail == null) {
            throw new RuntimeException("DiamondDetail not found");
        }
        diamondDetailService.deleteById(id);
    }

    @PutMapping("/diamonds/{diamondId}/details/{id}")
    public void updateDiamondDetail(@PathVariable int diamondId, @PathVariable int id, @RequestBody DiamondDetail diamondDetail) {
        DiamondDetail existingDiamondDetail = diamondDetailService.findById(id);
        if (existingDiamondDetail == null) {
            throw new RuntimeException("DiamondDetail not found");
        }
        existingDiamondDetail.setDiamond(diamondDetail.getDiamond());
//        existingDiamondDetail.setDetail(diamondDetail.getDetail());
        diamondDetailService.save(existingDiamondDetail);
    }
}
